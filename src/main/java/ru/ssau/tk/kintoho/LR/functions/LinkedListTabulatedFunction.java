package ru.ssau.tk.kintoho.LR.functions;


import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;


import java.util.NoSuchElementException;

import ru.ssau.tk.kintoho.LR.exceptions.InterpolationException;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Iterable<Point>, Serializable {
    @Serial
    private static final long serialVersionUID = -1485518412020327747L;
    private Node head;
    private int count;

    private static class Node implements Serializable {
        @Serial
        private static final long serialVersionUID = 7769496733099288029L;
        public Node next;
        public Node prev;
        public double x;
        public double y;
    }

    private void addNode(double x, double y) {
        Node joint = new Node();
        if (head == null) {
            head = joint;
            joint.x = x;
            joint.y = y;
            joint.prev = joint;
            joint.next = joint;
        } else {
            joint.next = head;
            joint.prev = head.prev;
            joint.x = x;
            joint.y = y;
            head.prev.next = joint;
            head.prev = joint;
        }
        count++;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues.length < 2 || yValues.length < 2) {
            throw new IllegalArgumentException("List size less than 2");
        }
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        for (int i = 0; i < xValues.length; i++) {
            this.addNode(xValues[i], yValues[i]);
        }
    }


    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("List size less than 2");
        }
        if (xFrom >= xTo) {
            throw new IllegalArgumentException("Invalid XFrom value");
        }
        double step = (xTo - xFrom) / (count - 1);
        if (xFrom < xTo) {
            for (int i = 0; i < count; i++) {
                addNode(xFrom, source.apply(xFrom));
                xFrom += step;
            }
        }
    }

    private Node getNode(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Index is out of bounds");
        }
        Node currentNode = head;

        for (int iterator = 0; iterator < index; iterator++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double getX(int index) {
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        int iterator = 0;
        while (iterator < count) {
            if (getNode(iterator).x == x) {
                return iterator;
            }
            iterator++;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        int iterator = 0;
        while (iterator < count) {
            if (getNode(iterator).y == y) {
                return iterator;
            }
            iterator++;
        }
        return -1;
    }

    @Override
    public int floorIndexOfX(double x) {
        if (x < head.x) {

            throw new IllegalArgumentException("Invalid X value");
        }
        for (int iterator = 0; iterator + 1 < count; iterator++) {
            if (getNode(iterator).next.x > x) {
                return iterator;
            }
        }
        return count;
    }

    @Override
    public double extrapolateLeft(double x) {

        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    public double extrapolateRight(double x) {

        return interpolate(x, head.prev.prev.x, head.prev.x, head.prev.prev.y, head.prev.y);
    }

    @Override
    public double interpolate(double x, int floorIndex) {
        if (x < head.x || x > head.prev.x) {
            throw new InterpolationException("X is out of bounds of interpolation");
        }
        return interpolate(x, getNode(floorIndex).x, getNode(floorIndex + 1).x, getNode(floorIndex).y, getNode(floorIndex + 1).y);
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<>() {
            private Node node = head;

            public boolean hasNext() {
                return (node != null);
            }

            @Override
            public Point next() {
                if (hasNext()) {
                    Point point = new Point(node.x, node.y);
                    node = (node != head.prev) ? node.next : null;
                    return point;
                } else {
                    throw new NoSuchElementException();
                }
            }

        };
    }


}



