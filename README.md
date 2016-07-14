## Platform variants

-	WEB (for .po files)
-   ANDROID
-   IOS

## Configuration file
```markdown
CSV_PATH=PATH/TO/CSV

TYPE_OF_PLATFORM_LOCATION=/PATH/TO/PROJECT/
TYPE_OF_PLATFORM_DEFAULT_LOCATION=en
TYPE_OF_PLATFORM_INCLUDING=test1.xml;test2.xml
TYPE_OF_PLATFORM_EXCLUDING=test1.xml;test2.xml
TYPE_OF_PLATFORM_CHARSET=test1.xml,UTF-16;test2.xml,UTF-16 //by default UTF-8
```

Where TYPE_OF_PLATFORM one of the Platform variants.

## How to use?

Download translater.jar and run it by one of the work type: FROM_CSV or TO_CSV.

For example:

```markdown
java -jar translater.jar /PATH/TO/CONFIG/config.properties FROM_CSV
```

