package framework;

import framework.impl.InFileRepository;
import framework.impl.InMemoryRepository;
import framework.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Main {
    private static final boolean USE_MEMORY_REPO = false;

    private static final CrudRepository<Produto> produtoRepo = USE_MEMORY_REPO
        ? new InMemoryRepository<>()
        : new InFileRepository<>("produtos.txt", new Produto("", 0.0));

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::criarJanela);
    }

    private static void criarJanela() {
        JFrame frame = new JFrame("Menu CRUD de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1));

        JButton btnAdicionar = new JButton("Adicionar Produto");
        JButton btnListar = new JButton("Listar Produtos");
        JButton btnConsultar = new JButton("Consultar Produto por ID");
        JButton btnAtualizar = new JButton("Atualizar Produto");
        JButton btnRemover = new JButton("Remover Produto");
        JButton btnSair = new JButton("Sair");

        btnAdicionar.addActionListener(Main::adicionarProduto);
        btnListar.addActionListener(Main::listarProdutos);
        btnConsultar.addActionListener(Main::consultarProduto);
        btnAtualizar.addActionListener(Main::atualizarProduto);
        btnRemover.addActionListener(Main::removerProduto);
        btnSair.addActionListener(
            new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            }
        );

        frame.add(btnAdicionar);
        frame.add(btnListar);
        frame.add(btnConsultar);
        frame.add(btnAtualizar);
        frame.add(btnRemover);
        frame.add(btnSair);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String solicitarEntrada(String mensagem) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(null, mensagem);
            if (entrada == null) {
                JOptionPane.showMessageDialog(null, "Operação cancelada!");
                return null;
            }
            if (entrada.isBlank()) {
                JOptionPane.showMessageDialog(null, "Nenhum caractere ou número foi digitado! Tente novamente.");
            } else {
                return entrada;
            }
        }
    }

    private static void adicionarProduto(ActionEvent e) {
        String nome = solicitarEntrada("Digite o nome do produto:");
        if (nome == null) return;

        for (Produto p : produtoRepo.findAll()) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                JOptionPane.showMessageDialog(null, "Erro: Já existe um produto com o nome '" + nome + "'.");
                return;
            }
        }

        String precoStr = solicitarEntrada("Digite o preço do produto:");
        if (precoStr == null) return;

        try {
            double preco = Double.parseDouble(precoStr);
            Produto produto = new Produto(nome, preco);
            produtoRepo.save(produto);
            JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Preço inválido! Operação cancelada.");
        }
    }

    private static void listarProdutos(ActionEvent e) {
        List<Produto> produtos = produtoRepo.findAll();
        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum produto encontrado.");
        } else {
            StringBuilder sb = new StringBuilder("--- Lista de Produtos ---\n");
            for (int i = 0; i < produtos.size(); i++) {
                sb.append("ID ").append(i).append(": ").append(produtos.get(i)).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    private static void consultarProduto(ActionEvent e) {
        String idStr = solicitarEntrada("Digite o ID do produto a consultar:");
        if (idStr == null) return;

        try {
            int id = Integer.parseInt(idStr);
            Produto produto = produtoRepo.findById(id);
            if (produto != null) {
                JOptionPane.showMessageDialog(null, "Produto encontrado: " + produto);
            } else {
                JOptionPane.showMessageDialog(null, "Produto com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID inválido! Operação cancelada.");
        }
    }

    private static void atualizarProduto(ActionEvent e) {
        String idStr = solicitarEntrada("Digite o ID do produto a atualizar:");
        if (idStr == null) return;
    
        try {
            int id = Integer.parseInt(idStr);
            Produto produto = produtoRepo.findById(id);
            if (produto == null) {
                JOptionPane.showMessageDialog(null, "Produto com ID " + id + " não encontrado.");
                return;
            }
    
            String novoNome = JOptionPane.showInputDialog(null, "Digite o novo nome do produto:", produto.getNome());
            if (novoNome == null) {
                JOptionPane.showMessageDialog(null, "Operação cancelada!");
                return;
            }            
    
            List<Produto> produtos = produtoRepo.findAll();
            for (Produto p : produtos) {
                if (p.getNome().equalsIgnoreCase(novoNome) && !p.equals(produto)) {
                    JOptionPane.showMessageDialog(null, "Erro: Já existe um produto com o nome '" + novoNome + "'.");
                    return;
                }
            }
    
            String novoPrecoStr = JOptionPane.showInputDialog(null, "Digite o novo preco do produto:", produto.getPreco());
            if (novoPrecoStr == null) {
                JOptionPane.showMessageDialog(null, "Operação cancelada!");
                return;
            }            
    
            double novoPreco = Double.parseDouble(novoPrecoStr);
    
            // **Nova verificação:**
            if (novoNome.equals(produto.getNome()) && novoPreco == produto.getPreco()) {
                JOptionPane.showMessageDialog(null, "O produto não foi alterado mas foi mantido com sucesso!");
                return;
            }
    
            // Atualiza o produto apenas se necessário
            produto.setNome(novoNome);
            produto.setPreco(novoPreco);
    
            produtoRepo.delete(id);
            produtoRepo.save(produto);
    
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID ou preço inválido! Operação cancelada.");
        }
    }    

    private static void removerProduto(ActionEvent e) {
        String idStr = solicitarEntrada("Digite o ID do produto a remover:");
        if (idStr == null) return;

        try {
            int id = Integer.parseInt(idStr);
            Produto produto = produtoRepo.findById(id);
            if (produto == null) {
                JOptionPane.showMessageDialog(null, "Produto com ID " + id + " não encontrado.");
                return;
            }

            produtoRepo.delete(id);
            JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID inválido! Operação cancelada.");
        }
    }
}