package sicredi.votacao.utils;

public class CpfUtils {

    public static String generateCPF() {
        int[] cpf = new int[11];
        for (int i = 0; i < 9; i++) {
            cpf[i] = (int) (Math.random() * 10);
        }

        cpf[9] = calculateVerifierDigit(cpf, 9);
        cpf[10] = calculateVerifierDigit(cpf, 10);

        StringBuilder cpfBuilder = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            cpfBuilder.append(cpf[i]);
        }

        return cpfBuilder.toString();
    }

    private static int calculateVerifierDigit(int[] cpf, int length) {
        int weight = length == 9 ? 10 : 11;
        int sum = 0;
        for (int i = 0; i < length; i++, weight--) {
            sum += cpf[i] * weight;
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

}
