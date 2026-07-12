package utilities;

import com.github.javafaker.Faker;

import java.util.UUID;

public class FakerSingleton {

    private static FakerSingleton instance;
    private final Faker faker;
    private final String testData;
    private final String agencyName;

    private FakerSingleton() {
        faker = new Faker();
        testData = faker.name().firstName() + "_" + UUID.randomUUID().toString().substring(0, 6);
        agencyName = faker.name().firstName() + "_" + UUID.randomUUID().toString().substring(0, 6);
    }

    public static FakerSingleton getInstance() {
        if (instance == null) {
            instance = new FakerSingleton();
        }
        return instance;
    }

    public String getTestData() {
        return testData;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public static class PassengerFactory {

        private static final Faker faker = new Faker();

        private PassengerFactory() {
        }

        public static String firstName() {
            return faker.name().firstName().replaceAll("[^a-zA-Z ]", "");
        }

        public static String lastName() {
            return faker.name().lastName().replaceAll("[^a-zA-Z ]", "");
        }

        public static String documentNumber() {
            return faker.number().digits(9);
        }
    }
}