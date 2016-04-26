package com.sulyagina.intern_task;


public class InternTask {
    // Задание 1
    // Ответ: d, f, g

    // Задание 2
    // Проблема в строке 2: не определен тип контейнера. Не можем просто так скастить byte[] в Object.
    // Можно Map<byte[], byte[]> (или другой тип, см. дальше)

    // Проблема в строке 4: byte[] не очень хорошо использовать ключом для HashMap, т.к массивы хешируются и
    // сравниваются не по значению и 2 одинаковых по значению ключа в хэшмапе не совпадут, вряд ли такое поведение
    // ожидаемо. Можно написать/(взять готовую) обертку для byte[], реализующую equals() и hashCode() с учетом значения.
    //
    // Замечания: Код плохо читаем из-за сбитого форматирования
    //            Можно подумать об использовании для этой цели ConcurrentHashMap

    // Задание 3
    class Node {
        int payload;
        Node next;
        public Node(int x, Node n) {
            payload = x;
            next = n;
        }
    }
    static Node reverse(Node head) {
        Node current = head;
        head = head.next;
        current.next = null;

        while (head != null) {
            Node next = head.next;
            head.next = current;
            current = head;
            head = next;
        }
        return current;
    }
}
