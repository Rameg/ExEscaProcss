package escar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class round {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// declaracao de variaveis
		int quantum, N, entrada, tempoAtual, execucao, q, temposFinais[], qteprocessos, novaDuracao, temposExecucao[],idProcessoAtual;
		ArrayList<Integer> ingressos, duracoes, processos, cpingressos, cpduracoes,prioridades;
		String ordem;
		double tempoMedioExecucao, tempoMedioEspera;
		int contTeste = 0;
		String formato, saida;
		DecimalFormat nf = new DecimalFormat("0.00");

		quantum = scanner.nextInt();
		N = scanner.nextInt();
		while (N != 0) {
			contTeste++;
			processos = new ArrayList();
			ingressos = new ArrayList();
			duracoes = new ArrayList();
			prioridades = new ArrayList();
			ordem = "";
			qteprocessos = N;
			temposFinais = new int[N];
			temposExecucao = new int[N];

			// lendo os processos
			for (int i = 0; i < N; i++) {
				entrada = scanner.nextInt();
				ingressos.add(entrada);
				entrada = scanner.nextInt();
				duracoes.add(entrada);
				entrada = scanner.nextInt();
				prioridades.add(entrada);
			}

			cpingressos = (ArrayList) ingressos.clone();
			cpduracoes = (ArrayList) duracoes.clone();

			// int tmpInicial = ingressos.get(0);
			tempoAtual = ingressos.get(0);
			processos = new ArrayList();

			processos = new ArrayList();

			while (qteprocessos > 0) {
				// percorrendo ingressos para descobrir processos que entram no
				// tempo
				// atual
				processos = new ArrayList();
				for (int i = 0; i < N; i++) {
					if (ingressos.get(i) != -1 && ingressos.get(i) <= tempoAtual) {
						// adicionar na lista de processos
						processos.add(i);
					}
				}

				if (processos.isEmpty()) {
					tempoAtual++;
				} else {
					// assumindo que o primeiro da lista eh o de menor
					// prioridade
					execucao = processos.remove(0);
					ordem += "P" + (execucao + 1) + " ";
					q = quantum;
					for (int i = 0; i < processos.size(); i++) {
						idProcessoAtual = processos.get(i);
						// se a prioridade do processo atual for menor do que a
						// menor
						// prioridade
						// ja encontrada

						if(prioridades.get(idProcessoAtual) < prioridades.get(execucao)) {
							// entao alteramos o processo que vai executar
							execucao = processos.get(i);
						}
						else if (prioridades.get(idProcessoAtual) == prioridades.get(execucao)) {
							while (q > 0 && duracoes.get(execucao) > 0) {
								tempoAtual++;
								q--;
								novaDuracao = duracoes.get(execucao) - 1;
								duracoes.set(execucao, novaDuracao);
							}
							if (duracoes.get(execucao) > 0) {
								// verificar primeiramente se algum processo entrou
								// durante
								// o
								// tempo de execucao
								for (int i1 = 0; i1 < N; i1++) {
									if (ingressos.get(i1) != -1 && ingressos.get(i1) <= tempoAtual) {
										processos.add(i1);
										ingressos.set(i1, -1);

									}
								}
								processos.add(execucao);
							} else {
								// processo acabou
								temposFinais[execucao] = tempoAtual;
								qteprocessos--;

							}
						}
						
					}
					System.out.println(
							"vou executar o P" + (execucao + 1) + " de prioridade " + prioridades.get(execucao));
					// tempo que o processo comeca a executar
					temposExecucao[execucao] = tempoAtual;

					tempoAtual += duracoes.get(execucao);

					// tempo que o processo termina de executar
					temposFinais[execucao] = tempoAtual;
					ingressos.set(execucao, -1);

					// define ordem de execucao
					ordem += "P" + (execucao + 1) + " ";

					qteprocessos--;
				}
			}

			// calculo de tempo medio de espera e execucao;
			tempoMedioExecucao = 0;
			tempoMedioEspera = 0;
			for (int i = 0; i < N; i++) {
				temposExecucao[i] = temposFinais[i] - cpingressos.get(i);
				tempoMedioExecucao += temposExecucao[i];
				tempoMedioEspera += temposExecucao[i] - cpduracoes.get(i);
			}

			tempoMedioExecucao = tempoMedioExecucao / N;
			tempoMedioEspera = tempoMedioEspera / N;
			System.out.println("Teste " + contTeste);

			formato = nf.format(tempoMedioExecucao);
			saida = "Tempo medio de execucao: " + formato + "s";
			saida = saida.replace(".", ",");
			System.out.println(saida);

			formato = nf.format(tempoMedioEspera);
			saida = "Tempo medio de espera: " + formato + "s";
			saida = saida.replace(".", ",");
			System.out.println(saida);

			System.out.println(ordem);
			System.out.println();
			N = scanner.nextInt();

		}
	}
}