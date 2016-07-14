#!/bin/bash
sep='---------------'
## declare an array variable
CURRENT_PATH=`pwd`
declare -a arr=("Android" "Xponia_iOS" "Web")
downloadFromRepo()
{
	log=$1
	branchs[0]=$2
	branchs[1]=$3
	branchs[2]=$4
	counter=0
	br=(${branchs[*]})
	echo $log + $pass
	for i in "${arr[@]}"
	do
		rm -r $i
		echo "Clone $i"
		d=`echo $i | sed s#/##`                               # remove trailing forward slash
		remoteRepo="https://$log@repo.arello-mobile.com/Xponia/$i"    # location of remote repo
		branch=${branchs[${counter}]}
		hg clone $remoteRepo -r $branch  # add remote repo as upstream
		echo -e "\n"
		let "counter += 1"
	done
}
​
generateConfig()
{
	echo "Generate config file"
	rm config.properties
	echo "CSV_PATH=$CURRENT_PATH/csv/
​
ANDROID_LOCATION=$CURRENT_PATH/Android/app/
ANDROID_INCLUDING=strings.xml
ANDROID_DEFAULT_LOCATION=en
​
WEB_LOCATION=$CURRENT_PATH/Web/
WEB_DEFAULT_LOCATION=en
​
IOS_LOCATION=$CURRENT_PATH/Xponia_iOS/Xponia/
IOS_DEFAULT_LOCATION=en
IOS_CHARSET=Localizable.strings,UTF-16">>config.properties
}
​
generateCsv()
{
	echo "Generate csv"
	java -jar translater.jar config.properties TO_CSV
	echo -e "\n"
}
​
removeProjectsFolders()
{
	for i in "${arr[@]}"
	do
		rm -r $i
	done
	rm config.properties
}
​
copyPoFiles()
{
	_base="${CURRENT_PATH}/Web/src/locale/en/LC_MESSAGES"
	_dfiles="$_base/*.po"
	_pofolder=${CURRENT_PATH}/po
	rm -r $_pofolder
	mkdir $_pofolder
	for f in $_dfiles
	do
		path=$(echo "$f" | sed "s/.*\///")
		cp "$_base/$path" "$_pofolder/$path"
	done
}
​
downloadFromRepo $1 $2 $3 $4 $5
generateConfig
generateCsv
copyPoFiles
removeProjectsFolders
exit $?
