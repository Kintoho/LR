package ru.ssau.tk.kintoho.LR.functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction {
    private Node head;
    private int count;

    private static class Node {
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

    LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        for (int i = 0; i < xValues.length; i++) {
            this.addNode(xValues[i], yValues[i]);
        }
    }


    LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        double step = (xTo - xFrom) / (count - 1);
        if (xFrom < xTo) {
            for (int i = 0; i < count; i++) {
                addNode(xFrom, source.apply(xFrom));
                xFrom += step;
            }
        }
    }

    private Node getNode(int index) {
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
            return 0;
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
        if (count == 1) {
            return x;
        }
        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    public double extrapolateRight(double x) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, head.prev.prev.x, head.prev.x, head.prev.prev.y, head.prev.y);
    }

    @Override
    public double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, getNode(floorIndex).x, getNode(floorIndex + 1).x, getNode(floorIndex).y, getNode(floorIndex + 1).y);
    }


}



