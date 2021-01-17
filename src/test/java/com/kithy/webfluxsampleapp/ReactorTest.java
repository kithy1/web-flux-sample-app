package com.kithy.webfluxsampleapp;

import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class ReactorTest {
    @Test
    public void createAFlux(){
        /**
         * utworzony ale nie ma jeszcze subskrybentów, dane nie będą przepływały
         * subskybcja typu reaktywnego pozwala na włączenie przepływu danych
         */
        Flux<String> fruitFlux = Flux.just("apple", "orange", "grape", "banana", "strawberry");
        /**
         * uruchomienie przepływu i operacja na elementach
         * lambda przekazana metodzie subscribe() to java.util.Consumer używany do utworzenia subskrybenta reaktywnego
         */
        fruitFlux.subscribe(s -> System.out.println("Fruits: " + s));

        StepVerifier.create(fruitFlux)
                .expectNext("apple")
                .expectNext("orange")
                .expectNext("grape")
                .expectNext("banana")
                .expectNext("strawberry")
                .verifyComplete();
    }
    @Test
    public void createAFlux_fromArray(){
        String[] fruits = new String[] {
                "apple", "orange", "grape", "banana", "strawberry"
        };
        Flux<String> fruitFlux = Flux.fromArray(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("apple")
                .expectNext("orange")
                .expectNext("grape")
                .expectNext("banana")
                .expectNext("strawberry")
                .verifyComplete();
    }
    @Test
    public void createAFlux_fromIterable(){
        List<String> fruits = List.of("apple", "orange", "grape", "banana", "strawberry");

        Flux<String> fruitFlux = Flux.fromIterable(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("apple")
                .expectNext("orange")
                .expectNext("grape")
                .expectNext("banana")
                .expectNext("strawberry")
                .verifyComplete();
    }
    @Test
    public void createAFlux_range(){
        /**
         * podajemy wartość początkową i końcową
         */
        Flux<Integer> intervalFlux = Flux.range(1, 5);

        StepVerifier.create(intervalFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }
    @Test
    public void createAFlux_interval(){
        /**
         * zamiast podawać wartość początkową i końcową,
         * definiuje się czas trwania lub częstotliwość emitowania wartości
         * wywołanie take(5) ogranicza wynik do pierwszych pięcu elementów
         */
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);

        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();

    }
    @Test
    public void mergeFluxes(){
        /**
         * delayElements() powoduje że element będzie emitowany z opóźnieniem
         */
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500));
        /**
         * delaySubscription() powoduje opóźnienie subskrypcji drugiego strumienia
         */
        Flux<String> foodFlux = Flux.just("lasagna", "lollipopss", "apples")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));
        /**
         * mergeWith() nie gwarantuje doskonałej synchronizacji między swoimi źródłami
         */
        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

        StepVerifier.create(mergedFlux)
                .expectNext("Garfield")
                .expectNext("lasagna")
                .expectNext("Kojak")
                .expectNext("lollipopss")
                .expectNext("Barbossa")
                .expectNext("apples")
                .verifyComplete();
    }
    @Test
    public void zipFluxes(){
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("lasagna", "lollipops", "apples");

        /**
         * jeżeli nie chcesz pracować z Tupple2, możesz dostarczyć metodzie zip()
         * obiekt funkcji generującej dowolnie wybrany obiekt na podstawie dwóch otrzymanych elementów
         */
        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

        StepVerifier.create(zippedFlux)
                .expectNextMatches(p ->
                        p.getT1().equals("Garfield") &&
                        p.getT2().equals("lasagna"))
                .expectNextMatches(p ->
                        p.getT1().equals("Kojak") &&
                        p.getT2().equals("lollipops"))
                .expectNextMatches(p ->
                        p.getT1().equals("Barbossa") &&
                        p.getT2().equals("apples"))
                .verifyComplete();
    }
    @Test
    public void zipFluxesToObject(){
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("lasagna", "lollipops", "apples");
        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " likes " + f);
        StepVerifier.create(zippedFlux)
                .expectNext("Garfield likes lasagna")
                .expectNext("Kojak likes lollipops")
                .expectNext("Barbossa likes apples")
                .verifyComplete();
                
    }
    @Test
    public void firstFlux(){
        Flux<String> slowFlux = Flux.just("turtle", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("rabbit", "puma", "squirrel");

        Flux<String> firstFlux = Flux.firstWithValue(slowFlux, fastFlux);

        StepVerifier.create(fastFlux)
                .expectNext("rabbit")
                .expectNext("puma")
                .expectNext("squirrel")
                .verifyComplete();

    }
    @Test
    public void skipAFew(){
        Flux<Integer> skipFlux = Flux.just(1, 2, 3, 4, 99, 100).skip(4);
        StepVerifier.create(skipFlux)
                .expectNext(99)
                .expectNext(100)
                .verifyComplete();
    }
    @Test
    public void skipAFewSeconds() {
        Flux<Integer> skipFlux = Flux.just(1, 2, 3, 4, 99, 100)
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(5));
        StepVerifier.create(skipFlux)
                .expectNext(99)
                .expectNext(100)
                .verifyComplete();
    }
    @Test
    public void take(){
        Flux<Integer> takeFlux = Flux.just(1, 2, 3, 4, 99, 100).take(4);
        StepVerifier.create(takeFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }
    @Test
    public void takeWithDuration(){
        Flux<Integer> takeFlux = Flux.just(1, 2, 3, 4, 99, 100)
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofSeconds(5));
        StepVerifier.create(takeFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }
    @Test
    public void filter(){
        Flux<String> fruitFlux = Flux.just("apple", "orange", "grape", "banana", "strawberry")
                .filter(s -> !s.contains("s"));

        StepVerifier.create(fruitFlux)
                .expectNext("apple")
                .expectNext("orange")
                .expectNext("grape")
                .expectNext("banana")
                .verifyComplete();

    }
    @Test
    public void distinct() {
        Flux<String> fruitFlux = Flux.just("apple", "orange", "grape", "banana", "strawberry", "apple", "banana")
                .distinct();
        StepVerifier.create(fruitFlux)
                .expectNext("apple", "orange", "grape", "banana", "strawberry")
                .verifyComplete();

    }
    @Test
    public void map() {
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(s -> {
                    String[] split = s.split("\\s");
                    return new Player(split[0], split[1]);
                });
        StepVerifier.create(playerFlux)
                .expectNext(new Player("Michael", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player("Steve", "Kerr"))
                .verifyComplete();
    }
    @Test
    public void flatMap(){
        /**
         * operacja flatMap() otrzymała wyrażenie lambda odpowiedzialne za przekształcenie przychodzącego obiektu
         * typu String na Mono. Następnie opreracja map() ma na celu przekształcenie obiektu Mono na typ Player.
         * Ostatnim zadaniem wykonywanym w obiekcie Mono jest wywołanie subscribeOn() wskazujace,
         * że poszczególne subskrypcje powinny się odbywać w wątku równoległym.
         * W efekcie operacje mapowania wielu otrzymanych obiektów typu String
         * mogą być przeprowadzone asynchronicznie i równolegle.
         *
         * Pozostałe metody klasy Schedulers:
         * .immediate() - wyoknuje subskrybcję w bieżącym wątku;
         * .single() - wykonuje subskrypcję w pojedynczym wątku wielokrotnego użycia.
         * Ten sam watek jest używany dla wszystkich wywołań;
         * .newSingle() - wykonuje subskrypcję w oddzialnym wątku dla każdego wywołania;
         * .elastic() - wykonuje subskrypcję w wątku roboczym pochodzacym z puli.
         * Nowy wątek roboczy jest tworzony wedle potrzeb, a nieaktywny zostaje usuniety po pewnym czasie bezczynności;
         * .paralell() - wykonuje subskrybcję w wątku roboczym pobranym z puli określonej wielkości powiązanej
         * z liczba rdzeni w procesorze;
         */
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Mono.just(n)
                .map(s -> {
                    String[] split = s.split("\\s");
                    return new Player(split[0], split[1]);
                })
                .subscribeOn(Schedulers.parallel()));

        List<Player> players = List.of(new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr"));

        StepVerifier.create(playerFlux)
                .expectNextMatches(players::contains)
                .expectNextMatches(players::contains)
                .expectNextMatches(players::contains)
                .verifyComplete();
    }

    @Test
    @Disabled("test ma za zadanie wykazać, że przetwarzanie elementów odbywa się asynchronicznie, więc nie są one zwracane po kolei")
    public void flatMap1(){
        /**
         * operacja flatMap() otrzymała wyrażenie lambda odpowiedzialne za przekształcenie przychodzącego obiektu
         * typu String na Mono. Następnie opreracja map() ma na celu przekształcenie obiektu Mono na typ Player.
         * Ostatnim zadaniem wykonywanym w obiekcie Mono jest wywołanie subscribeOn() wskazujace,
         * że poszczególne subskrypcje powinny się odbywać w wątku równoległym.
         * W efekcie operacje mapowania wielu otrzymanych obiektów typu String
         * mogą być przeprowadzone asynchronicznie i równolegle.
         */
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Mono.just(n)
                        .map(s -> {
                            String[] split = s.split("\\s");
                            return new Player(split[0], split[1]);
                        })
                        .subscribeOn(Schedulers.parallel()));

        List<Player> players = List.of(new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr"));

        StepVerifier.create(playerFlux)
                .expectNext(new Player("Michael", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player("Steve", "Kerr"))
                .verifyComplete();
    }
    @Test
    public void buffer(){
        Flux<String> fruitFlux = Flux.just("apple", "orange", "grape", "banana", "strawberry");
        /**
         * operacja buffer() powoduje określenie maksymalnej liczby elementów pobieranych ze strumienia wejściowego
         */
        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

        StepVerifier.create(bufferedFlux)
                .expectNext(List.of("apple", "orange", "grape"))
                .expectNext(List.of("banana", "strawberry"))
                .verifyComplete();
    }
    @Test
    public void parallelProcessing(){
        /**
         * buforowanie wartości z typu reaktywnego Flux na niereaktywną listę
         * i wykożystanie flatMap() powoduje, że poszczególne listy będą mogły być przetwarzane równolegle;
         */
        Flux.just("apple", "orange", "grape", "banana", "strawberry")
                .buffer(3)
                .flatMap(x ->
                        Flux.fromIterable(x)
                .map(String::toUpperCase)
                .subscribeOn(Schedulers.parallel())
                .log()
                ).subscribe();
        /**
         * 21:04:15.920 [parallel-1] INFO reactor.Flux.SubscribeOn.1 - onNext(APPLE)
         * 21:04:15.922 [parallel-1] INFO reactor.Flux.SubscribeOn.1 - onNext(ORANGE)
         * 21:04:15.921 [parallel-2] INFO reactor.Flux.SubscribeOn.2 - onNext(BANANA)
         * 21:04:15.923 [parallel-2] INFO reactor.Flux.SubscribeOn.2 - onNext(STRAWBERRY)
         * 21:04:15.922 [parallel-1] INFO reactor.Flux.SubscribeOn.1 - onNext(GRAPE)
         * 21:04:15.925 [parallel-1] INFO reactor.Flux.SubscribeOn.1 - onComplete()
         * 21:04:15.924 [parallel-2] INFO reactor.Flux.SubscribeOn.2 - onComplete()
         */
    }
    @Test
    public void collectList() {
        Flux<String> fruitsFlux = Flux.just("apple", "orange", "grape", "banana", "strawberry");
        Mono<List<String>> listMono = fruitsFlux.collectList();

        StepVerifier.create(listMono)
                .expectNext(List.of("apple", "orange", "grape", "banana", "strawberry"))
                .verifyComplete();
    }
    @Test
    public void collectMap(){
        Flux<String> animalFlux = Flux.just("elephant", "koala", "eagle", "kangaroo");
        Mono<Map<Character, String>> mapMono = animalFlux.collectMap(s -> s.charAt(0));

        StepVerifier.create(mapMono)
                .expectNextMatches(map ->
                        map.size() == 2 && !map.containsValue("koala") && map.get('k').equals("kangaroo"))
                .verifyComplete();
    }
    @Test
    public void all() {
        Flux<String> animalFlux = Flux.just("elephant", "koala", "eagle", "kangaroo");
        Mono<Boolean> booleanMono = animalFlux.all(a -> a.contains("a"));

        StepVerifier.create(booleanMono)
                .expectNext(true)
                .verifyComplete();
    }
    @Test
    public void any() {
        Flux<String> animalFlux = Flux.just("elephant", "koala", "eagle", "kangaroo");
        Mono<Boolean> booleanMono = animalFlux.any(a -> a.contains("o"));

        StepVerifier.create(booleanMono)
                .expectNext(true)
                .verifyComplete();
    }

    @EqualsAndHashCode
    private class Player {
        String name;
        String lastName;

        public Player(String name, String lastName) {
            this.name = name;
            this.lastName = lastName;
        }
    }
}
