# Navegador Rápido

Aplicativo Android leve que transforma a página inicial do projeto em uma experiência nativa instalável. A interface é carregada localmente dentro de um `WebView`, enquanto os links externos continuam funcionando como navegação web.

## Recursos

- Relógio e data em português na tela inicial.
- Busca no Google com suporte ao teclado do celular.
- Atalhos para Betfair, YouTube, Gmail, WhatsApp, GitHub, ChatGPT e Maps.
- Tema claro/escuro persistido no dispositivo.
- Navegação interna com botão voltar do Android.
- Suporte a links `mailto:`, `tel:`, `sms:`, `geo:` e `intent:`.
- Restauração do estado do WebView após rotação ou recriação da Activity.

## Requisitos

- Android Studio Hedgehog ou mais recente.
- JDK 17.
- Android SDK Platform 35.

## Executar e gerar o APK

Abra a pasta no Android Studio e execute a configuração `app`. Para gerar o APK de debug localmente, use:

```bash
gradle assembleDebug
```

O arquivo será criado em `app/build/outputs/apk/debug/app-debug.apk`.

Também existe um workflow em `.github/workflows/build-apk.yml`. Cada push na branch `main` gera automaticamente um artefato chamado `Navegador-Rapido-debug-apk` na aba **Actions** do GitHub.

## Estrutura

- `app/src/main/java/.../MainActivity.kt`: Activity nativa e integração com WebView.
- `app/src/main/assets/index.html`: interface inicial do navegador.
- `app/src/main/res/layout/activity_main.xml`: layout nativo que hospeda o WebView.
- `app/src/main/AndroidManifest.xml`: permissões e configuração da aplicação.
