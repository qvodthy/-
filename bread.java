/**
 * Created by Rzz on 2015/11/15.
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class bread {
    public static void main(String[] args) throws IOException {
        //try
        //{
        tBread[] atBread = new tBread[100];
        int[] x = new int[100];
        float iCostPerformance = 0;
        float tempCostPerformance;
        int iArrayLength;
        int i, j, m, n, count;
        boolean flag;
        tBread[] list = new tBread[6];
        Scanner pDataBase = new Scanner(Paths.get("src\\bread.txt"));

        String acTempName;
        int iTempEnergy;
        float fTempPossibilityOfBigSuccess;
        int iTempCost;
        int iBreadNumber;
        i = 0;
        //while(EOF != fscanf(pDataBase,"%s%d%f%d",acTempName,&iTempEnergy,&fTempPossibilityOfBigSuccess,&iTempCost,))
        while (pDataBase.hasNextLine()) {
            acTempName = pDataBase.next();
            //System.out.println(acTempName);
            iTempEnergy = pDataBase.nextInt();
            fTempPossibilityOfBigSuccess = pDataBase.nextFloat();
            iTempCost = pDataBase.nextInt();
            iBreadNumber = pDataBase.nextInt();
            for (j = 0; j < iBreadNumber; j++) {
                atBread[i] = new tBread(acTempName, iTempEnergy, fTempPossibilityOfBigSuccess, iTempCost);
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
                    /*list[n].fPossibilityOfBigSuccess = atBread[n].fPossibilityOfBigSuccess;
		            list[n].iCost = atBread[n].iCost;
		            list[n].iEnergy = atBread[n].iEnergy;
		            //strcpy(list[n].acName,atBread[n].acName);
		            list[n].acName = atBread[n].acName;*/

                list[n] = new tBread(atBread[n].acName, atBread[n].iEnergy, atBread[n].fPossibilityOfBigSuccess, atBread[n].iCost);

                sumOfCost += atBread[n].iCost;
                sumOfEnergy += atBread[n].iEnergy;
                sumOfPossibility += atBread[n].fPossibilityOfBigSuccess;
            }
            sumOfPossibility = (sumOfPossibility > 1) ? 1 : sumOfPossibility;
            tempCostPerformance = sumOfEnergy * (1 + 0.5F * sumOfPossibility) / sumOfCost;
            if (tempCostPerformance > iCostPerformance) {
                iCostPerformance = tempCostPerformance;
                for (n = 0; n < i; n++) {
                    //printf("%s\t%d\t%f\t%d\n",list[n].acName,list[n].iEnergy,list[n].fPossibilityOfBigSuccess,list[n].iCost);
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
                                    list[m].iCost = atBread[n].iCost;
                                    list[m].iEnergy = atBread[n].iEnergy;
                                    list[m].fPossibilityOfBigSuccess = atBread[n].fPossibilityOfBigSuccess;
                                    //strcpy(list[m].acName,atBread[n].acName);
                                    list[m].acName = atBread[n].acName;
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

