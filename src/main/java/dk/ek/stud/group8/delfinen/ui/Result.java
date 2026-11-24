package dk.ek.stud.group8.delfinen.ui;

public sealed interface Result<T> {
    record Ok<T>(T value) implements Result<T> {
    }

    record Err<T>(String message) implements Result<T> {
    }
}
