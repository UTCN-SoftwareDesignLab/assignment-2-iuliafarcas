package com.lab4.demo;

import com.lab4.demo.bookstore.model.Book;
import com.lab4.demo.bookstore.model.Genre;
import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.bookstore.model.dto.GenreDTO;
import com.lab4.demo.user.dto.UserListDTO;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newBookDTO;
        } else if (cls.equals(Genre.class)) {
            supplier = TestCreationFactory::newGenre;
        }else if (cls.equals(GenreDTO.class)) {
                supplier = TestCreationFactory::newGenreDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    /*private static Item newItem() {
        return Item.builder()
                .id(randomLong())
                .name(randomString())
                .description(randomString())
                .build();
    }

    private static ItemDTO newItemDTO() {
        return ItemDTO.builder()
                .id(randomLong())
                .name(randomString())
                .description(randomString())
                .build();
    }

    private static ReviewDTO newReviewDTO() {
        return ReviewDTO.builder()
                .id(randomLong())
                .text(randomString())
                .reviewer(randomString())
                .build();
    }*/

    private static Book newBook() {
        return Book.builder()
                .title(randomString())
                .author(randomString())
                .price(randomBoundedFloat(100))
                .stock(randomLong())
                //.genre(Genre.builder().genre(randomString()).build())
                .genre(randomString())
                .build();
    }

    private static BookDTO newBookDTO() {
        //Genre genre = Genre.builder().genre(randomString()).build();

        return BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .stock(randomLong())
                .genre(randomString())
                .build();
    }

    private static Genre newGenre(){
        return Genre.builder()
                .genre(randomString())
                .build();
    }

    private static GenreDTO newGenreDTO(){
        return GenreDTO.builder()
                .id(randomLong())
                .genre(randomString())
                .build();
    }


    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static float randomBoundedFloat(float upperBound) {
        return new Random().nextFloat() * upperBound;
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
