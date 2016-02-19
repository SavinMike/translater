Вид конфигурационного файла:

CSV_PATH=PATH/TO/CSV

%s_LOCATION=/PATH/TO/PROJECT

%s_DEFAULT_LOCATION=en

%s_INCLUDING=test1.xml;test2.xml

%s_EXCLUDING=test1.xml;test2.xml

%s_CHARSET=test1.xml,UTF-16;test2.xml,UTF-16 //По дефолту стоит UTF-8

Для работы необходимо скачать translater.jar и запустить в конкретном режиме. FROM_CSV или TO_CSV

java -jar translater.jar /PATH/TO/CONFIG/config.properties FROM_CSV

