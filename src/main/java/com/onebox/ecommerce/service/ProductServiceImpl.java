package com.onebox.ecommerce.service;

import com.onebox.ecommerce.dto.ProductDTO;
import com.onebox.ecommerce.dto.ResultActionCRUD;
import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.model.Product;
import com.onebox.ecommerce.model.ProductPK;
import com.onebox.ecommerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {this.productRepository = productRepository;}

    @Override
    public ResultActionCRUD addProduct(Cart cart, List<ProductDTO> productDTOList) {

        try {

            for (ProductDTO productDTO : productDTOList) {

                ProductPK productPK = new ProductPK();
                productPK.setIdCart(cart.getIdCart());
                productPK.setIdProduct(productDTO.getIdProduct());

                Product product = new Product();
                product.setId(productPK);
                product.setAmount(productDTO.getAmount());
                product.setDescription(productDTO.getDescription());

                productRepository.save(product);

            }

            return ResultActionCRUD.builder().isOk(true).build();

        } catch (Exception e) {

            log.error(e.getMessage());
            return ResultActionCRUD
                    .builder().isOk(false)
                    .missatge(e.getMessage())
                    .build();

        }

    }

    @Override
    public List<ProductDTO> findAllProductByCart(Cart cart) {

        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> productList = productRepository.findAllByCart_IdCart(cart.getIdCart());

        for (Product product : productList) {

            productDTOList.add(ProductDTO.builder()
                    .idProduct(product.getId().getIdProduct())
                    .amount(product.getAmount())
                    .description(product.getDescription())
                    .build()
            );

        }

        return productDTOList;

    }

}
