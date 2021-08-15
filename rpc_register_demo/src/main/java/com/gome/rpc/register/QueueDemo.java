package com.gome.rpc.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @create 2021-08-14-18:42
 */
public class QueueDemo {
    public static final Logger LOG = LoggerFactory.getLogger (QueueDemo.class);
    private static Node root = new Node (null);

    private static Node head;
    private static Node last;
//    private static Node last = root;
    static {
    head = last = new Node (null);
}

    static class Node<E> {
        E e;
        Node<E> next;

        public Node(E e) {
            this.e = e;
        }
    }

    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<Integer> ();
        for (int i = 1; i < 6; i++) {
            enqueue(new Node(new Integer (i)));
            ints.add (new Integer (i));
        }


//        Node n1 = new Node (new Integer (1));
//        root.next = n1;
//
//        Node n2 = new Node (new Integer (2));
//        n1.next = n2;
//
//        Node n3 = new Node (new Integer (3));
//        n2.next = n3;
//
//        Node n4 = new Node (new Integer (4));
//        n3.next = n4;
//
//        Node n5 = new Node (new Integer (5));
//        n4.next = n5;

        LOG.info ("the node is {}", root);

        LOG.info ("the Logger is result");

//        Object head = dequeue1 ();
//        LOG.info ("the head is {}", head);
//
//        Object h2 = dequeue1 ();
//        LOG.info ("the head2 is {}", h2);

         remove (3);
         LOG.info ("the queue is {}", head);

         remove(5);
         LOG.info ("the queue is {}", head);


    }

    static void enqueue(Node node) {
        last = last.next = node;
    }

    static <E> E dequeue(){
        // 先進先出
        if (head.next == null) {
            return null;
        }

        // 獲取要彈出的元素
        Node<E> h = head;
        Node<E> first = h.next;
        h.next = h;
        E e = first.e;
        head = first;
        first.e = null;
        return e;
    }

    static <E> E dequeue1() {
        // 斷開式寫法
        if (head.next == null) {
            return null;
        }

        Node<E> h = head;
        Node<E> first = h.next;
        Node<E> sndNode = first.next;

        // 將head.next設置為sndNode
        h.next = sndNode;
//        將first的next設置為空，
        LOG.info ("the first data is {}", first);
        E e = first.e;
        first.next = first;
        first.e = null;
        return e;
    }

    static <E> boolean remove(E e) {
        if (e == null) {
            return false;
        }
        // 查找元素是否存在，并且確定其位置
        for (Node<E> trail = head, p = head.next; p != null; trail = p, p = p.next) {
            E e1 = p.e;
            if (e1 != null && e1.equals (e)) {
                unlink (trail, p);
                return true;
            }
        }
        return false;
    }

    // 移除節點需要確定其左右節點，尤其是其父節點的確定
    static void unlink(Node trail, Node p) {
        p.e = null;
        // 獲取當前值得next
        Node next = p.next;
        // 將其trail的next指定為其孫類
        trail.next = next;
        // 如果刪除last， 則重置last
        if(p == last) {
            last = trail;
        }
        // 刪除p節點
        p.next = p;
    }

}
