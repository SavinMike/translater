#!/bin/bash
sep='---------------'
## declare an array variable
CURRENT_PATH=`pwd`
if [ -n "$1" ]; then
	source $CURRENT_PATH/$1
else
	source $CURRENT_PATH/translater.cfg
fi
generateConf()
{
	echo "Generate config file"
	modulePath=$android_module_path
	rm config.properties
	echo "CSV_PATH=$CURRENT_PATH/csv/">>config.properties
	if [ -n "$android_module_path" ]; then
	   echo "
ANDROID_LOCATION=$CURRENT_PATH/$android_project_repo/$android_module_path/
ANDROID_INCLUDING=$android_translater_xml
ANDROID_DEFAULT_LOCATION=$default_language">>config.properties
	fi
	if [ -n "$ios_project_path" ]; then
		echo "
IOS_LOCATION=$CURRENT_PATH/$ios_project_repo/$ios_project_path/
IOS_DEFAULT_LOCATION=$default_language
IOS_CHARSET=$ios_charset">>config.properties
	fi
	if [ -n "$web_project_repo" ]; then
		echo "
WEB_LOCATION=$CURRENT_PATH/$web_project_repo/
WEB_DEFAULT_LOCATION=$default_language">>config.properties
	fi
}
downloadFromRepo()
{
	branches[0]=$android_branch
	branches[1]=$ios_branch
	branches[2]=$web_branch

	projectsPath[0]=$android_project_repo
	projectsPath[1]=$ios_project_repo
	projectsPath[2]=$web_project_repo
	counter=0
	for i in "${projectsPath[@]}"
	do
		if [ -n "$i" ]; then
			rm -r $i
			echo "Clone $i"
			d=`echo $i | sed s#/##`                               # remove trailing forward slash
			remoteRepo="https://$username@$repo_url/$repo_name/$i"    # location of remote repo
			branch=${branches[${counter}]}
			git clone -b $branch $remoteRepo  # add remote repo as upstream
			echo -e "\n"
		fi
		let "counter += 1"
	done
}
fromCsv()
{
	echo "Generate zip from csv"
	java -jar translater.jar config.properties FROM_CSV
	echo -e "\n"
}
removeProjectsFolders()
{
	projectsPath[0]=$android_project_repo
	projectsPath[1]=$ios_project_repo
	projectsPath[2]=$web_project_repo
	for i in "${projectsPath[@]}"
	do
		if [ -n "$i" ]; then
			rm -r $i
		fi
	done
	rm config.properties
}
copyWebFiles()
{
	if [ -n "$web_project_repo" ]; then
		_base="${CURRENT_PATH}/$web_project_repo/src/locale/"
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
	fi
}
copyIOSFiles()
{
	_base="${CURRENT_PATH}/$ios_project_repo/$ios_project_path/"
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
	_base="${CURRENT_PATH}/$android_project_repo/$android_module_path/src"
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
downloadFromRepo
generateConf
fromCsv
copyWebFiles
copyIOSFiles
copyAndroidFiles
#removeProjectsFolders
exit $?
