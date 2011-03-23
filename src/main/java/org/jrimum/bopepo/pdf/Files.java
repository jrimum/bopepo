/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:17:54
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:17:54
 * 
 */

package org.jrimum.bopepo.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Strings;

/**
 * <p>
 * Utilitário para manipular arquivos e fluxos de arquivos.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class Files {

	private static final int DEFAULT_BUFFER_SIZE = 4096;

	/**
	 * <p>
	 * Transforma um array de bytes em um arquivo.
	 * </p>
	 * 
	 * @param pathName
	 *            - Caminho do arquivo para onde os bytes serão escritos.
	 * @param bytes
	 *            - Bytes a serem copiados.
	 * 
	 * @return Objeto File com o conteúdo sendo o dos bytes
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 *             Caso {@codepathName} ou {@code bytes} seja null, vazio ou
	 *             contenha apenas espaços em branco
	 * @since 0.2
	 */
	public static File bytesToFile(String pathName, byte[] bytes)
			throws FileNotFoundException, IOException {

		Strings.checkNotBlank(pathName);
		Objects.checkNotNull(bytes);

		return bytesToFile(new File(pathName), bytes);
	}

	/**
	 * <p>
	 * Transforma um array de bytes em um arquivo.
	 * </p>
	 * 
	 * @param file
	 *            - arquivo para onde os bytes serão escritos.
	 * @param bytes
	 *            - Bytes a serem copiados.
	 * 
	 * @return Objeto File com o conteúdo sendo o dos bytes
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 *             Caso {@codepathName} ou {@code bytes} seja null, vazio ou
	 *             contenha apenas espaços em branco
	 * 
	 * @since 0.2
	 */
	public static File bytesToFile(File file, byte[] bytes)
			throws FileNotFoundException, IOException {

		Objects.checkNotNull(file);
		Objects.checkNotNull(bytes);

		if (file.length() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(
					"TAMANHO DO ARQUIVO MAIOR DO QUE O SUPORTADO: "
							+ Integer.MAX_VALUE);
		}

		OutputStream out = new FileOutputStream(file);

		out.write(bytes);
		out.flush();
		out.close();

		return file;
	}

	/**
	 * <p>
	 * Transforma um array de bytes em um <code>ByteArrayOutputStream</code>.
	 * </p>
	 * 
	 * @param bytes
	 *            - Bytes que serão escritos no objeto ByteArrayOutputStream
	 * 
	 * @return ByteArrayOutputStream ou null
	 * 
	 * @throws IOException
	 * @throws IllegalArgumentException
	 *             Caso os {@code bytes} sejam {@code null}.
	 * 
	 * @since 0.2
	 */
	public static ByteArrayOutputStream bytesToStream(byte[] bytes)
			throws IOException {

		Objects.checkNotNull(bytes);

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		byteOut.write(bytes);

		return byteOut;
	}

	/**
	 * Retorna o conteúdo do arquivo em um array de bytes.
	 * 
	 * @param file
	 * @return Conteúdo em um array de bytes.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 *             Caso o {@code file} seja {@code null}.
	 */
	public static byte[] fileToBytes(File file) throws IOException {

		Objects.checkNotNull(file);

		InputStream is = new FileInputStream(file);

		byte[] bytes = new byte[(int) file.length()];

		int offset = 0;
		int numRead = 0;

		while ((offset < bytes.length)
				&& ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
			offset += numRead;
		}

		is.close();

		Objects.checkArgument(offset == bytes.length,
				"Não foi possível completar a leitura do arquivo: "
						+ file.getName());

		return bytes;
	}

	/**
	 * Retorna o conteúdo do {@code InputStream} em um array de bytes.
	 * 
	 * @param input
	 * @return Conteúdo em um array de bytes.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 *             Caso o {@code input} seja {@code null}.
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {

		Objects.checkNotNull(input);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

		int count = 0;
		int n = 0;

		while (-1 != (n = input.read(buffer))) {

			output.write(buffer, 0, n);
			count += n;
		}

		return output.toByteArray();
	}
}
