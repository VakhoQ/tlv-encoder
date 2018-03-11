
# tlv-encoder
 TLV (tag-length-value) communication protocol implementation.


### Requirements
* Java version 1.8 or higher 
* maven 3.x or gradle 4.x

### Build with maven

In order to build project with maven you should check out **build-project-with-maven** branch.

```bash
git checkout build-project-with-maven
```

run the following command in order to build project:

```bash
mvn package
```


### Build with gradle

In order to build project with gradle you should check out **master** branch.

```bash
git checkout master
```

if **you have gradle installed** in your local machine run the following command:

```bash
gradle build
```


if you **don't have gradle installed** in your local machine, you can use gradle wrapper to build project.

```bash
./gradlew build
```



### How to encode 
```java
TLVAbstractData numericTypeValue = new NumericTypeValue("key", 1);
TLVAbstractData stringTypeValue = new StringTypeValue("key", "value");
List<TLVAbstractData> input = Arrays.asList(numericTypeValue, stringTypeValue);
byte[] output = new TLVParserImpl().encode(input);
```

### How to decode
```java
byte [] stringTypeInput = new byte[]{5, 4, 77, 84, 73, 68, 2, 0, -48, 16};
byte [] numericTypeInput = new byte[]{1, 4, 77, 84, 73, 68, 1, 0, 1};
List<StringTypeValue> output = new TLVParserImpl().decode(stringTypeInput);
List<NumericTypeValue> output = new TLVParserImpl().decode(numericTypeInput);
```

You can see various encoding/decoding examples in Groovy test cases.
