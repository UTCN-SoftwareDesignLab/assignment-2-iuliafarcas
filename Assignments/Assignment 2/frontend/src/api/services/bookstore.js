import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/bookstore/books", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/bookstore/books", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(book) {
    return HTTP.patch(BASE_URL + `/bookstore/books/${book.id}`, book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(book) {
    return HTTP.delete(BASE_URL + `/bookstore/books/${book.id}`, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  sell(book) {
    return HTTP.post(BASE_URL + "/bookstore/books/sell", book, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  pdf() {
    return HTTP.get(BASE_URL + "/bookstore/books/export/PDF", { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  csv() {
    return HTTP.get(BASE_URL + "/bookstore/books/export/CSV", { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
};
