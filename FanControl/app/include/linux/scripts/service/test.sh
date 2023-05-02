#!/bin/bash

dir1="./rep1"
dir2="./rep2"

# Vérifier si les deux répertoires existent
if [ ! -d "$dir1" ] || [ ! -d "$dir2" ]; then
    echo "Erreur : au moins un des deux répertoires n'existe pas."
    exit 1
fi

# Comparer le contenu des deux répertoires
diff_output=$(diff -q -r "$dir1" "$dir2")

# Vérifier si les deux répertoires ont le même contenu
if [ -z "$diff_output" ]; then
    echo "Les deux répertoires ont le même contenu."
else
    echo "Attention : les deux répertoires n'ont pas le même contenu."
    echo "$diff_output"
fi
