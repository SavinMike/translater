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
fromCsv()
{
	echo "Generate csv"
	java -jar translater.jar config.properties FROM_CSV
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
copyWebFiles()
{
	_base="${CURRENT_PATH}/Web/src/locale/"
	_dfiles="$_base/*.po"
	_baseResult=${CURRENT_PATH}
	mkdir $_baseResult
	_pofolder=$_baseResult/webStrings
	echo "Start copying web files to $_pofolder folder"
	mkdir $_pofolder
	for _dir in $(find $_base* -maxdepth 0 -type d );
	do
		_baseLocalDir="$_dir/LC_MESSAGES"
		_files="$_baseLocalDir/*.po"
		_language="${_dir##*/}"
		mkdir $_pofolder/$_language
		mkdir "$_pofolder/$_language/LC_MESSAGES"
		for f in $(find $_files -maxdepth 0 -type f )
		do
			path=$(echo "$f" | sed "s/.*\///")
			cp "$_baseLocalDir/$path" "$_pofolder/$_language/LC_MESSAGES/$path"
		done
	done
	zip -r $_baseResult/webStrings.zip webStrings
	rm -r $_pofolder
	echo "Done"
}

copyIOSFiles()
{
	_base="${CURRENT_PATH}/Xponia_iOS/Xponia"
	_dfolders="$_base/*.lproj"
	_baseResult=${CURRENT_PATH}
	_folder=$_baseResult/iosStrings
	echo "Start copying IOS files to $_folder folder"
	mkdir $_folder
	for _dir in $(find $_dfolders -maxdepth 0 -type d );
	do
		_language="${_dir##*/}"
		_files="$_dir/*.strings"
		mkdir $_folder/$_language
		for f in $(find $_files -maxdepth 0 -type f )
		do
			path=$(echo "$f" | sed "s/.*\///")
			cp "$_dir/$path" "$_folder/$_language/$path"
		done
	done
	zip -r $_baseResult/iosStrings.zip iosStrings
	rm -r $_folder
	echo "Done"
}

copyAndroidFiles()
{
	_base="${CURRENT_PATH}/Android/app/src"
	_dfolders="$_base/*"
	_baseResult=${CURRENT_PATH}
	_folder=$_baseResult/androidStrings
	echo "Start copying Android files to $_folder folder"
	mkdir $_folder
	for _dir in $(find $_dfolders -maxdepth 0 -type d );
	do
		_language="${_dir##*/}"
		if [[ $_language == *"Test"* ]]
		then
			echo "Test"
		else
			mkdir "$_folder/$_language"
			mkdir "$_folder/$_language/res"
			mkdir "$_folder/$_language/res/values"

			_files="$_dir/res/values/strings.xml"
			cp "$_files" "$_folder/$_language/res/values/strings.xml"
		fi
	done
	zip -r $_baseResult/androidStrings.zip androidStrings
	rm -r $_folder
	echo "Done"
}
​
​
downloadFromRepo $1 $2 $3 $4 $5
generateConfig
fromCsv
copyWebFiles
copyIOSFiles
copyAndroidFiles
removeProjectsFolders
exit $?
