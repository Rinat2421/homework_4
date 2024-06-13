import java.util.Random;

public class Main {
    public static int bossHealth = 1500;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250, 280, 700, 200, 350, 290};
    public static int[] heroesDamage = {25, 15, 10, 15, 3, 10, 0, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int roundNumber = 0;
    public static boolean bossStunned = false;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            round();
        }
    }
    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        showStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = damage * coeff;
                    if (i==3){
                        System.out.println("Critical heal: " + heroesAttackType[i] + " " + damage);
                    }
                    else {
                    System.out.println("Critical damage: " + heroesAttackType[i] + " " + damage);}
                }
                if (i==3){
                    int minHp= 100;
                    int minHpHero = -1;
                    for (int j = 0; j < heroesHealth.length ; j++) {
                        if (heroesHealth[j]<minHp && heroesHealth[j]>0 && j!=3){
                            minHp=heroesHealth[j];
                            minHpHero=j;
                        }
                    }
                        if (minHpHero>-1){
                        heroesHealth[minHpHero]+=damage;
                        System.out.println("Medic лечит " + heroesAttackType[minHpHero]);}

                }
                else if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                    if (i==6) {
                        Random random = new Random();
                        bossStunned = random.nextBoolean();
                    }
                }
            }
        }
    }

    public static void bossAttacks() {
        if (bossStunned == true) {
            System.out.println("Boss оглушен в этом ходу.");
            bossStunned=false;
        }
        else {
        for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && heroesHealth[4] > 0) {
                    if (i == 5) {
                        Random random = new Random();
                        boolean luck = random.nextBoolean();
                        if (luck == false) {
                            if (heroesHealth[i] - bossDamage * 4.0 / 5.0 < 0) {
                                heroesHealth[i] = 0;
                                heroesHealth[4] = heroesHealth[4] - bossDamage / 5;
                            } else {
                                heroesHealth[i] = heroesHealth[i] - bossDamage * 4 / 5;
                                heroesHealth[4] = heroesHealth[4] - bossDamage / 5;
                            }
                        }
                        System.out.println("Lucky увернулся в этом ходу.");
                    } else if (heroesHealth[i] - bossDamage * 4.0 / 5.0 < 0) {
                        heroesHealth[i] = 0;
                        heroesHealth[4] = heroesHealth[4] - bossDamage / 5;
                        if (heroesHealth[6] > 0) {
                            heroesHealth[i] += heroesHealth[6];
                            heroesHealth[6] = 0;
                            System.out.println("Witcher оживил " + heroesAttackType[i]);
                        }
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage * 4 / 5;
                        heroesHealth[4] = heroesHealth[4] - bossDamage / 5;
                    }
                } else {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                        if (heroesHealth[6] > 0) {
                            heroesHealth[i] += heroesHealth[6];
                            heroesHealth[6] = 0;
                            System.out.println("Witcher оживил " + heroesAttackType[i]);
                        }
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }
    }


    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------------");
        String defence;
        if(bossDefence == null) {
            defence = "None";
        } else {
            defence = bossDefence;
        }
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "None" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++)
        if (i==3){
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " heal: " + heroesDamage[i]);
        }
        else
        {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}