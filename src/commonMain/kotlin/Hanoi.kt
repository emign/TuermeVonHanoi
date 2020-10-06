fun solveHanoi(scheiben: Int, start: Tower, zwischen : Tower, ziel: Tower) {
    if (scheiben == 1){
        moveDisk(start, ziel)
    }

    else if (scheiben > 1){
        solveHanoi(scheiben - 1, start, ziel, zwischen)
        moveDisk(start, ziel)
        solveHanoi(scheiben - 1, zwischen, start, ziel)

    }
}