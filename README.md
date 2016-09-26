## Platform variants

-	WEB (for .po files)
-   ANDROID
-   IOS

## Configuration file
```markdown
CSV_PATH=PATH/TO/CSV

WEB_LOCATION=/PATH/TO/PROJECT/
WEB_DEFAULT_LOCATION=en
WEB_CHARSET=test1.xml,UTF-16;test2.xml,UTF-16 //by default UTF-8

IOS_LOCATION=/PATH/TO/PROJECT/
IOS_DEFAULT_LOCATION=en
IOS_CHARSET=test1.xml,UTF-16;test2.xml,UTF-16 //by default UTF-8
IOS_INCLUDING=test1.xml;test2.xml
IOS_EXCLUDING=test1.xml;test2.xml

ANDROID_LOCATION=/PATH/TO/PROJECT/
ANDROID_DEFAULT_LOCATION=en
ANDROID_CHARSET=test1.xml,UTF-16;test2.xml,UTF-16 //by default UTF-8
ANDROID_INCLUDING=test1.xml;test2.xml
ANDROID_EXCLUDING=test1.xml;test2.xml
```

Где: 
1. CSV_PATH - путь, где хроняться или будут хрониться csv файлы.

2. LOCATION - путь до проекта (Web, Ios, Android соответственно).

3. DEFAULT_LOCATION - дефолтная локализация проекта.

4. CHARSET - если какой либо файл имеет кодировку отличную от UTF-8, его необходимо прописать в этой переменной. Указывается как ключ-значение, где ключ это имя файла, а значение кодировка файла.

5. INCLUDING/EXLUDING - по умолчанию в csv файл будут конвертироваться все файлы лежащие в локализируемых папках. Если необходимо, чтобы использовались только некоторые из них, то можно включить/исключить в/из конвертацию необходимые файлы.

## How to use?
Для работы с утилитой необходимо:
1. Скачать файл translater.jar.

2. Создать конфигурационный файл, на примере описаного выше (если какие-то платформы не нужны их можно не описывать). 

3. Запусть jar файл со следующими аргументами

	1. Путь до конфигурационного файла

	2. Режим работы утилиты: 

		* FROM_CSV - программа будет изменять файлы проекта лежащего в папке WEB(ANDROID,IOS)_LOCATION в соответствие с csv/tsv файлами, которые лежат в папке CSV_PATH

		* TO_CSV - программа будет генерировать TSV файлы для проектов, которые указаны в конфигурационном файле.

For example:

```markdown
java -jar translater.jar /PATH/TO/CONFIG/config.properties FROM_CSV
```

## PS
Очень важно сохранять наименование tsv файлов. По названиям файла будут изменяться локализованные файлы проектов.

## PPS
Разделителем в csv файлах является табуляция.

