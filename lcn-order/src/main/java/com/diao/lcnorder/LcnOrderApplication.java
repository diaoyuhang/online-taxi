package com.diao.lcnorder;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDistributedTransaction
public class LcnOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LcnOrderApplication.class, args);
	}

}
