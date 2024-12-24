    package com.khanenka.cucumbers;

    import com.khanenka.cucumbers.entity.Cucumber;
    import com.khanenka.cucumbers.entity.Jar;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import reactor.core.publisher.Flux;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

    //Eсть сущность Огурец, которая имеет поля
    //1. размер (объем).
    //
    //Eсть сущность Банка которая имеет поля
    //1. лист огурцов (содержит огурцы, которые входят в данную банку)
    //2. максимальное объем банки (сколько в нее может поместиться огурцов.
    //
    //Необходимо написать метод, который на вход принимает Flax<Огурцов> и возвращает Flax<Банок>.
    // Данный метод должен сгруппировать огурцы по банкам.
    //
    //Примечания.
    //1) если цельный огурец не влазит в банку, отрезать от огурца столько, сколько может поместиться в эту банку, а часть которая осталась, положить в следующую банку.
    @SpringBootApplication
    public class CucumbersApplication {

//        public static Flux<Jar> groupCucumbersIntoJars(Flux<Cucumber> cucumbers) {
//            final double jarMaxVolume = 10.0;
//
//
//            return cucumbers
//                    .flatMap(cucumber -> {
//                        double cucumberSize = cucumber.getSize();
//                        List<Cucumber> cucumberParts = new ArrayList<>();
//
//                        while (cucumberSize > 0) {
//                            double partToAdd = Math.min(cucumberSize, jarMaxVolume);
//                            cucumberParts.add(new Cucumber(partToAdd));
//                            cucumberSize -= partToAdd;
//                        }
//
//
//                        return Flux.fromIterable(cucumberParts);
//                    })
//                    .collect(() -> new ArrayList<>(List.of(new Jar(jarMaxVolume))),
//                            (jars, cucumberPart) -> {
//                                Jar lastJar = jars.get(jars.size() - 1);
//                                if (lastJar.getRemainingVolume() == 0) {
//                                    lastJar = new Jar(jarMaxVolume);
//                                    jars.add(lastJar);
//                                }
//
//                                lastJar.addCucumber(cucumberPart);
//                            })
//                    .flatMapMany(jars -> Flux.fromIterable(jars));
//        }

     //   public static void main(String[] args) {
//            Flux<Cucumber> cucumbers = Flux.just(
//                    new Cucumber(3.0),
//                    new Cucumber(5.0),
//                    new Cucumber(10.2)
//            );
//
//            // Группируем огурцы в банки
//            Flux<Jar> jars = groupCucumbersIntoJars(cucumbers);
//
//            // Подписываемся на результат
//            jars.subscribe(jar -> {
//                System.out.println("Jar volume: " + jar.getRemainingVolume());
//                System.out.println("Contents of the jar:");
//                jar.getCucumbers().forEach(cucumber ->
//                        System.out.println("  Cucumber size: " + Math.round(cucumber.getSize()*100)/100));
//            }, error -> System.err.println("Error: " + error.getMessage()), () -> {
//                // Выводим общее количество банок после завершения потока
//                System.out.println("Total jars: " + jars.count().block());
//            });
//        }


//        }
//    }

        public static List<Jar> groupCucumbersIntoJars(List<Cucumber> cucumbers) {
            final double jarMaxVolume = 10.0; // Максимальный объем банки


            return cucumbers.stream()
                    .reduce(new ArrayList<Jar>(),
                            (jars, cucumber) -> {
                                double cucumberSize = cucumber.getSize();

                                while (cucumberSize > 0) {
                                    Jar currentJar;
                                    if (jars.isEmpty() || jars.get(jars.size() - 1).getRemainingVolume() == 0) {
                                        currentJar = new Jar(jarMaxVolume);
                                        jars.add(currentJar);
                                    } else {
                                        currentJar = jars.get(jars.size() - 1);
                                    }

                                    double remainingJarVolume = currentJar.getRemainingVolume();
                                    double partToAdd = Math.min(cucumberSize, remainingJarVolume);
                                    currentJar.addCucumber(new Cucumber(partToAdd));
                                    cucumberSize -= partToAdd;
                                }
                                return jars;
                            },
                            (jars1, jars2) -> {
                                jars1.addAll(jars2);
                                return jars1;
                            });
        }

        public static void main(String[] args) {
            List<Cucumber> cucumbers = Arrays.asList(
            new Cucumber(3.0),
                    new Cucumber(5.0),
                    new Cucumber(10.2)
            );

            List<Jar> jars = groupCucumbersIntoJars(cucumbers);

           jars.stream().forEach(System.out::println);
        }
    }