Set-Location "./../FanControl/src/jvmMain/resources/drawable"

# Récupération de la liste des fichiers
$files = Get-ChildItem -Recurse

# Parcours de chaque fichier
foreach ($file in $files)
{
    # Découpage du nom du fichier en deux parties
    $parts = $file -split '_FILL'

    # Si le tableau contient deux éléments, cela signifie que le motif a été trouvé
    if ($parts.Count -eq 2)
    {
        # Réassemblage du nom du fichier avec l'extension .svg
        $newName = $parts[0] + '.svg'

        # Renommage du fichier
        Rename-Item -Path $file.FullName -NewName $newName
    }
}

# Retour au répertoire de départ
Set-Location $PSScriptRoot