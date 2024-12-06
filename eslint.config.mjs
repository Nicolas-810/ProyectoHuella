import globals from 'globals';
import pluginJs from '@eslint/js';
import pkg from '@typescript-eslint/parser';  // Importación por defecto
import pluginTs from '@typescript-eslint/eslint-plugin';
import pluginVue from 'eslint-plugin-vue';

// Usamos el parser de TypeScript desde el paquete por defecto
const tsParser = pkg.Parser;

export default [
  {
    files: ["**/*.{js,mjs,cjs,ts,vue}"],
    languageOptions: {
      globals: globals.browser,  // Definir los globals para el entorno de navegador
    },
  },
  pluginJs.configs.recommended,  // Reglas recomendadas para JS
  pluginTs.configs.recommended,  // Reglas recomendadas para TypeScript
  pluginVue.configs["flat/essential"],  // Reglas esenciales para Vue
  {
    files: ["**/*.vue"],
    languageOptions: {
      parser: tsParser,  // Usar el parser de TypeScript para archivos Vue
    },
  },
];