/**
 * Created by Rzz on 2015/11/15.
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Hashtable;

class bread{
    String acName;
    int iEnergy;
    float fPossibilityOfBigSuccess;
    int iCost;

    public bread(String sName, int iEnergy, float fPossibilityOfBigSuccess, int iCost){
        this.acName = sName;
        this.iEnergy = iEnergy;
        this.fPossibilityOfBigSuccess = fPossibilityOfBigSuccess;
        this.iCost = iCost;
    }

}

class breadCostTableKey {
    int iBreadLevel;
    int iChampionLevel;

    public void setBreadLevel(int breadLevel){
        iBreadLevel = breadLevel;
    }

    public void setChampionLevel(int championLevel){
        iChampionLevel = championLevel;
    }
}

class breadCostTable{
    Hashtable table;

    public breadCostTable(){
        table = new Hashtable(30);
    }

    public void put(breadCostTableKey breadCostTableKey,int cost){
        String key = new String();

        key = String.valueOf(breadCostTableKey.iBreadLevel) + String.valueOf(breadCostTableKey.iChampionLevel);
        table.put(key,cost);
    }

    public int get(breadCostTableKey breadCostTableKey){
        String key = new String();

        key = String.valueOf(breadCostTableKey.iBreadLevel) + String.valueOf(breadCostTableKey.iChampionLevel);
        return (int) table.get(key);
    }
}

public class main {
    public static void main(String[] args) throws IOException {
        bread[] aBread = new bread[100];
        int[] x = new int[100];
        float iCostPerformance = 0;
        float tempCostPerformance;
        int iArrayLength;
        int i, j, m, n, count;
        boolean flag;
        bread[] list = new bread[6];
        Scanner pDataBase = new Scanner(Paths.get("src\\bread.txt"));
        Scanner configScanner = new Scanner(Paths.get("src\\config.txt"));
        Scanner breadCostTableScanner = new Scanner(Paths.get("src\\breadCostTable.txt"));
        breadCostTable breadCostTable = new breadCostTable();
        breadCostTableKey breadCostTableKey = new breadCostTableKey();
        String acTempName;
        int iTempEnergy;
        float fTempPossibilityOfBigSuccess;
        int iTempBreadLevel;
        int iBreadNumber;
        int iTempCost = 0;

        for(i = 1; breadCostTableScanner.hasNextLine(); i++){
            breadCostTableKey.setBreadLevel(i);
            for(j = 2; j <= 6; j++){
                breadCostTableKey.setChampionLevel(j);
                iTempCost = breadCostTableScanner.nextInt();
                breadCostTable.put(breadCostTableKey, iTempCost);
            }
        }

        configScanner.next();
        configScanner.next();
        breadCostTableKey.setChampionLevel(configScanner.nextInt());
        i = 0;
        while (pDataBase.hasNextLine()) {
            acTempName = pDataBase.next();
            iTempEnergy = pDataBase.nextInt();
            fTempPossibilityOfBigSuccess = pDataBase.nextFloat();
            iTempBreadLevel = pDataBase.nextInt();
            iBreadNumber = pDataBase.nextInt();
            breadCostTableKey.setBreadLevel(iTempBreadLevel);
            iTempCost = breadCostTable.get(breadCostTableKey);
            for (j = 0; j < iBreadNumber; j++) {
                aBread[i] = new bread(acTempName, iTempEnergy, fTempPossibilityOfBigSuccess, iTempCost);
                i++;
            }
        }
        iArrayLength = i;

        for (i = 1; i <= 6; i++) {
            for (j = 0; j < i; j++)
                x[j] = 1;
            for (; j < iArrayLength; j++)
                x[j] = 0;
            int sumOfCost = 0;
            float sumOfEnergy = 0;
            float sumOfPossibility = 0;
            for (n = 0; n < i; n++) {
                list[n] = new bread(aBread[n].acName, aBread[n].iEnergy, aBread[n].fPossibilityOfBigSuccess, aBread[n].iCost);

                sumOfCost += aBread[n].iCost;
                sumOfEnergy += aBread[n].iEnergy;
                sumOfPossibility += aBread[n].fPossibilityOfBigSuccess;
            }
            sumOfPossibility = (sumOfPossibility > 1) ? 1 : sumOfPossibility;
            tempCostPerformance = sumOfEnergy * (1 + 0.5F * sumOfPossibility) / sumOfCost;
            if (tempCostPerformance > iCostPerformance) {
                iCostPerformance = tempCostPerformance;
                for (n = 0; n < i; n++) {
                    System.out.printf("%s\t%d\t%f\t%d\n", list[n].acName, list[n].iEnergy, list[n].fPossibilityOfBigSuccess, list[n].iCost);
                }
                System.out.printf("the expected energy is %.1f,while the total cost is %d,which makes the cost performance is %f.\n", sumOfEnergy * (1 + 0.5 * sumOfPossibility), sumOfCost, iCostPerformance);
                System.out.println();
            }

            flag = false;
            while (flag) {
                for (j = 0; j < (iArrayLength - 1); j++) {
                    if (0 == x[j])
                        continue;
                    else {
                        if (1 == x[j + 1])
                            continue;
                        else {
                            x[j] = 0;
                            x[j + 1] = 1;

                            for (n = 0, count = 0; n < j; n++) {
                                if (1 == x[n])
                                    count++;
                            }
                            for (n = 0; n < count; n++)
                                x[n] = 1;
                            for (; n < j; n++)
                                x[n] = 0;

                            for (n = 0, m = 0; n < iArrayLength; n++) {
                                if (1 == x[n]) {
                                    list[m].iCost = aBread[n].iCost;
                                    list[m].iEnergy = aBread[n].iEnergy;
                                    list[m].fPossibilityOfBigSuccess = aBread[n].fPossibilityOfBigSuccess;
                                    list[m].acName = aBread[n].acName;
                                    m++;
                                }
                            }

                            sumOfCost = 0;
                            sumOfEnergy = 0;
                            sumOfPossibility = 0;
                            for (n = 0; n < m; n++) {
                                sumOfCost += list[n].iCost;
                                sumOfEnergy += list[n].iEnergy;
                                sumOfPossibility += list[n].fPossibilityOfBigSuccess;
                            }
                            sumOfPossibility = (sumOfPossibility > 1) ? 1 : sumOfPossibility;
                            tempCostPerformance = sumOfEnergy * (1 + 0.5F * sumOfPossibility) / sumOfCost;
                            if (tempCostPerformance > iCostPerformance) {
                                iCostPerformance = tempCostPerformance;
                                for (n = 0; n < i; n++) {
                                    System.out.printf("%s\t%d\t%f\t%d\n", list[n].acName, list[n].iEnergy, list[n].fPossibilityOfBigSuccess, list[n].iCost);
                                }
                                System.out.printf("the expected energy is %.1f,while the total cost is %d\n,which makes the cost performance is %f.\n", sumOfEnergy * (1 + 0.5 * sumOfPossibility), sumOfCost, iCostPerformance);
                                System.out.println();
                            }

                            break;
                        }
                    }
                }

                if ((iArrayLength - 1) == j)
                    flag = false;
            }

        }
    }
}