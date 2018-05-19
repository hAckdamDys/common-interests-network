class User {
    constructor(username) {
        this.username = username;
    }
}

class Comment {
    constructor(author, contents, pluses) {
        this.author = author;
        this.contents = contents;
        this.pluses = pluses;
    }
}

class Post {
    constructor(author, contents, comments, pluses) {
        this.author = author;
        this.contents = contents;
        this.comments = comments;
        this.pluses = pluses;
    }
}

const posts = [];

class Category {
    constructor(name) {
        this.name = name;
    }
}

const subscribedCategories = [new Post(new User("adam"), "Hello!", [new Comment(new User("adam213", "hi", 1))], 12)];
const selectedCategories = [];