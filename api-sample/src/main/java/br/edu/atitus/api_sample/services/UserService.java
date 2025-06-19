package br.edu.atitus.api_sample.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.atitus.api_sample.entities.UserEntity;
import br.edu.atitus.api_sample.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	
	private final UserRepository repository;
	
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}



	public UserEntity save(UserEntity user) throws Exception {
		if (user == null) 
			throw new Exception("objeto não pode ser nulo");
		if (user.getName() == null || user.getName().isEmpty())
			throw new Exception("nome não pode ser nulo ou vazio");	
		user.setName(user.getName().trim());
		
		if (user.getEmail() == null || user.getEmail().isEmpty())
			throw new Exception("nome não pode ser nulo ou vazio");	
		user.setName(user.getName().trim());
		//usar regex para validar o e-mail (texto@texto.texto)
		//TODO Validar o e-mail
		
		if (user.getPassword() == null || 
				user.getPassword().isEmpty() ||
				user.getPassword().length() < 8)
			throw new Exception("senha não pode ser nula ou vazia");
		
		//TODO Validar a senha (tamanho mínimo, caracteres especiais, etc.)
			
		if (user.getType() == null)
			throw new Exception("Tipo de usuário inválido");
		
		if (repository.existsByEmail(user.getEmail()))
			throw new Exception("E-mail já cadastrado");
			
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		repository.save(user);
		
		return user;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		return user;
	}

	

}
