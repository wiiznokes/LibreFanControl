Set-Location "./../FanControl/src/jvmMain/resources/drawable"

# Récupération de la liste des fichiers
$files = Get-ChildItem -Recurse

# recuppere le dernier nombre dans une string
$regex = '(\d+)(?=\.)'

# Parcours de chaque fichier
foreach ($file in $files)
{
    # Découpage du nom du fichier en deux parties
    $parts = $file -split '_FILL'

    # Si le tableau contient deux éléments, cela signifie que le motif a été trouvé
    if ($parts.Count -eq 2)
    {
        $size = [regex]::Match($parts[1], $regex).Value

        # Réassemblage du nom du fichier avec l'extension .svg
        $newName = $parts[0] + $size + '.svg'

        # Renommage du fichier
        Rename-Item -Path $file.FullName -NewName $newName
    }
}

# Retour au répertoire de départ
Set-Location $PSScriptRoot