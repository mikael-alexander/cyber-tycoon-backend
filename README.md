# Cyber Tycoon Backend

Bem-vindo ao **Cyber Tycoon Backend**! 🚀  
Este repositório contém a API e a lógica de negócio para o jogo **Cyber Tycoon**, responsável por gerenciar usuários, recursos, progressão e interações do jogo.

---

## 📌 Funcionalidades
- Autenticação e gerenciamento de usuários
- Controle de recursos e economia do jogo
- Progressão de níveis e conquistas
- Integração com banco de dados
- Endpoints RESTful para comunicação com o frontend

---

## 🛠️ Tecnologias Utilizadas
- **Node.js** com **Express**
- **MongoDB** (ou outro banco de dados configurado)
- **JWT** para autenticação
- **Docker** para containerização (opcional)
- Testes com **Jest** ou **Mocha**

---

## 🚀 Instalação e Uso

### Pré-requisitos
- Node.js (>= 18)
- NPM ou Yarn
- MongoDB em execução local ou remoto

### Passos
```bash
# Clone o repositório
git clone https://github.com/mikael-alexander/cyber-tycoon-backend.git

# Acesse a pasta
cd cyber-tycoon-backend

# Instale as dependências
npm install

# Configure variáveis de ambiente
cp .env.example .env

# Execute o servidor
npm run dev
