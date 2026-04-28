package mn.csm311.lab12.task2;

/**
 * ДААЛГАВАР 2b: Null Object Pattern.
 *
 * Энэ класс нь Logger интерфэйсийг "юу ч хийхгүй" байдлаар хэрэгжүүлнэ.
 * Ингэснээр хэрэглэгч код нь `if (logger != null)` гэж шалгах шаардлагагүй
 * болно — logger үргэлж байгаа гэж үзэж болно.
 */
public class NullLogger implements Logger {

    @Override
    public void log(String message) {
        // Intentionally no-op.
    }

    @Override
    public int logCount() {
        return 0;
    }
}
