package com.example.dealer;

import com.example.dealer.entity.CarEntity;
import com.example.dealer.entity.MarkEntity;
import com.example.dealer.entity.PriceEntity;
import com.example.dealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class DealerApplication implements CommandLineRunner{

	@Autowired
	private CarRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DealerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CarEntity carEntity1 = new CarEntity();
		carEntity1.setColor("red");
		carEntity1.setModelName("Focus");

		CarEntity carEntity2 = new CarEntity();
		carEntity2.setColor("blue");
		carEntity2.setModelName("Clio");

		MarkEntity markEntity1 = new MarkEntity();
		markEntity1.setName("Ford");
		markEntity1.setCars(Arrays.asList(carEntity1));

		MarkEntity markEntity2 = new MarkEntity();
		markEntity2.setName("Renault");
		markEntity2.setCars(Arrays.asList(carEntity2));

		PriceEntity priceEntity1 = createPriceEntity(18, 19, 15000.00);
		priceEntity1.setCar(carEntity1);
		PriceEntity priceEntity2 = createPriceEntity(21, 23, 17000.00);
		priceEntity2.setCar(carEntity1);
		PriceEntity priceEntity3 = createPriceEntity(18, 20, 17000.00);
		priceEntity3.setCar(carEntity2);

		carEntity1.setMark(markEntity1);
		carEntity1.setPriceEntities(Arrays.asList(priceEntity1, priceEntity2));

		carEntity2.setMark(markEntity2);
		carEntity2.setPriceEntities(Arrays.asList(priceEntity3));

		repository.save(carEntity1);
		repository.save(carEntity2);
	}

	private PriceEntity createPriceEntity(int dayOfInitDate, int dayOfEndDate, double price){
		PriceEntity priceEntity = new PriceEntity();
		priceEntity.setInitDate(new GregorianCalendar(2021, Calendar.MAY, dayOfInitDate).getTime());
		priceEntity.setEndDate(new GregorianCalendar(2021, Calendar.MAY, dayOfEndDate).getTime());
		priceEntity.setPrice(new Double(price));
		return priceEntity;
	}
}
