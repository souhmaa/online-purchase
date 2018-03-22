package com.kata.purchase;

import lombok.Getter;
import lombok.Setter;

import static com.kata.purchase.Constants.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@Setter
public class Product {

    private String id;
    private String label;
    private Double price;
    private boolean available;

    public boolean isValid() {
        return !isEmpty(id) && price != null;
    }

    public static class ProductBuilder {
        private String id;
        private String label;
        private Double price;
        private boolean available;

        public ProductBuilder(String id) {
            this.id = id;
        }

        public ProductBuilder label(String label) {
            this.label = label;
            return this;
        }

        public ProductBuilder price(Double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder available(boolean available) {
            this.available = available;
            return this;
        }

        public Product build() {
            Product product =  new Product();
            product.setId(this.id);
            product.setLabel(this.label);
            product.setAvailable(this.available);
            product.setPrice(this.price);
            return product;
        }
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.label)
                .append(MESSAGE_SEPARATOR)
                .append(this.price)
                .append(EURO)
                .append(MESSAGE_SEPARATOR)
                .append(this.available ? AVAILABLE : NOT_AVAILABLE);
        return sb.toString();
    }

}
