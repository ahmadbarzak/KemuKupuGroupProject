#!/bin/bash

maxWords(){
	# This function gets the maximum amount of words
	# 5 is usual max but can be less if there are not 5 words in word list
	# $1 file of chosen topic
  local file=$1
  local NUM_WORDS=`cat $file | wc -l`
  local maxWords=5

  if [[ $NUM_WORDS -lt 5 ]]; then
    maxWords=$NUM_WORDS
  fi

  return $maxWords
}

getTestWords(){
	# This functions gets list of words from file
	# Uses randomWords to generate random word
	# $1 file of chosen topic
	# $2 maximum number of test words to generate
	local file=$1
	rm src/script/tempWords # Resets word list
	local maxWords=$2
	for (( i = 1; i<=$maxWords ; i++ )); do
		randomWord $file
	done
}

randomWord(){
	# This functions generates a random word from a file and checks for duplicates in test list
	# Called from getTestWords
	# Redirects word into src/script/tempWords file to be used by script
	# $1 file of chosen topic
	local file=$1
  local NUM_WORDS=`cat $file | wc -l`

	local randNum=$((1 + $RANDOM % $NUM_WORDS))
  local randWord=`sed "${randNum}q;d" $file`

	while grep -qxF "$randWord" src/script/tempWords; do
		randNum=$((1 + $RANDOM % $NUM_WORDS))
		randWord=`sed "${randNum}q;d" $file`
	done

  echo "$randWord" >> src/script/tempWords
}

checkSpelling(){
	local testWordNumber=$1
	local attempt=$2
	local attemptNum=$3

	local actual=`sed "${testWordNumber}q;d" src/script/tempWords`

	shopt -s nocasematch
	if [[ $attempt == $actual ]] && [ $attemptNum -eq 1 ]; then
		return 1
	elif [[ $attempt != $actual ]] && [ $attemptNum -eq 1 ]; then
		return 2
	elif [[ $attempt == $actual ]] && [ $attemptNum -eq 2 ]; then
		return 3
	elif [[ $attempt != $actual ]] && [ $attemptNum -eq 2 ]; then
		return 4
	fi
}




option=$1
wordNum=$2
attemptNumber=$3

case $option in
	"getWords" )
		# This case generates the random words to be tested from the chosen topic
		touch src/script/tempWords
		topic=$2
		maxWords $topic
		maxWordCount=$?
		getTestWords $topic $maxWordCount
	;;
	"play" )
	# Run at every word attempt, plays x1 for first try, x2 for second try
	word=`sed "${wordNum}q;d" src/script/tempWords`
	for (( i = 0; i < $attemptNumber; i++ )); do
		echo $word | festival --tts
	done
	;;
	"wordCheck" )
		# Run when spelling attempt is submitted
		# Returns echo of exit status referring to words progress status
		spellingAttempt=$4
		checkSpelling $wordNum $spellingAttempt $attemptNumber
		wordStatus=$?
		echo "$wordStatus"
	;;
esac
