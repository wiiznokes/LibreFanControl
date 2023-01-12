# Change the current location to "./../FanControl/src/jvmMain/resources/drawable"
Set-Location "./../FanControl/src/jvmMain/resources/drawable"

# Get a list of files
$files = Get-ChildItem -Recurse

# Get the last number in a string
$regex = '(\d+)(?=\.)'

# Loop through each file
foreach ($file in $files)
{
    # Split the file name into two parts
    $parts = $file -split '_FILL'

    # If the array contains two elements, it means the pattern was found
    if ($parts.Count -eq 2)
    {
        $size = [regex]::Match($parts[1], $regex).Value

        # Reassemble the file name with the .svg extension
        $newName = $parts[0] + $size + '.svg'

        # Rename the file
        Rename-Item -Path $file.FullName -NewName $newName
    }
}

# Return to the starting directory
Set-Location $PSScriptRoot