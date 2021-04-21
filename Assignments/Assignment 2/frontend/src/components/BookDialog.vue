<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create item" : "Edit item" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="item.title" label="Title" />
          <v-text-field v-model="item.author" label="Author" />
          <v-text-field v-model="item.genre" label="Genre" />
          <v-text-field v-model="item.price" label="Price" />
          <v-text-field v-model="item.stock" label="Stock" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteBook">Delete Book</v-btn>
          <v-btn @click="sellBook">Sell Book</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.bookstore
          .create({
            title: this.item.title,
            author: this.item.author,
            genre: this.item.genre,
            price: this.item.price,
            stock: this.item.stock,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.bookstore
          .edit({
            id: this.item.id,
            title: this.item.title,
            author: this.item.author,
            genre: this.item.genre,
            price: this.item.price,
            stock: this.item.stock,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteBook(){
      api.bookstore
          .delete({
            id: this.item.id,
            title: this.item.title,
            author: this.item.author,
            genre: this.item.genre,
            price: this.item.price,
            stock: this.item.stock,
          })
          .then(() => this.$emit("refresh"));
    },
    sellBook(){
      api.bookstore
          .sell({
            id: this.item.id,
            title: this.item.title,
            author: this.item.author,
            genre: this.item.genre,
            price: this.item.price,
            stock: this.item.stock,
          })
          .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>
