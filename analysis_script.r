data <- read.csv("output.txt", sep=",")
colnames(data) <- c("numVertices", "edmondsKarpTime", "ericksonTime")

topnum <- max(data$numVertices)
numVertices <- seq(1,topnum,1)
edmondsAverages <- rep(NA,topnum-1)
edmondsStd <- rep(NA,topnum-1)

ericksonAverages <- rep(NA,topnum-1)
ericksonStd <- rep(NA,topnum-1)

for (i in 1:topnum){
    edmondsAverages[i] <- mean(data[data$numVertices==i,2], na.rm=TRUE)
    edmondsStd[i] <- var(data[data$numVertices==i,2], na.rm=TRUE)^(0.5)
    print(i)
}
for (i in 1:topnum){
    ericksonAverages[i] <- mean(data[data$numVertices==i,3])
    ericksonStd[i] <- var(data[data$numVertices==i,3])^(0.5)
}

resultingData <- data.frame(numVertices, edmondsAverages, edmondsStd, ericksonAverages, ericksonStd)

numVertices <- numVertices[1:1500]
edmondsAverages <- edmondsAverages[1:1500]
ericksonAverages <- ericksonAverages[1:1500]

ekt = na.omit(cbind(numVertices, edmondsAverages))
ert = na.omit(cbind(numVertices, ericksonAverages))

plot(numVertices, edmondsAverages, pch=13, xlab="Number of Vertices", ylab="Average Number of NanoSeconds")
lines(lowess(ekt[,1],ekt[,2]), col="blue", lty=2, lwd=6)
points(numVertices, ericksonAverages)
lines(lowess(ert[,1],ert[,2]), col="red", lwd=6)
legend(0, 60000000, c("Edmonds-Karp","Erickson"), lty=c(2,1), col=c("blue", "red"), lwd=2, cex=1.2)
