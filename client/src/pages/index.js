import Head from 'next/head'
import { Inter } from '@next/font/google'
import styles from '@/styles/Home.module.css'

import API from '@/lib/api'

const inter = Inter({ subsets: ['latin'] })

function Signup() {

	const handleSignup = async e => {
		e.preventDefault();

		const userData = {
			firstName: e.target.firstname.value,
			lastName: e.target.lastname.value,
			email: e.target.email.value,
			password: e.target.password.value,
		};

		API.signup(userData, data => console.log(data));
	};

	return (
		<div className={styles.homeform}>
			<form onSubmit={handleSignup}>
				<div>
					<label for="firstname">First Name</label>
					<input name="firstname" placeholder="John" />
				</div>
				<div>
					<label for="lastname">Last Name</label>
					<input name="lastname" placeholder="Smith" />
				</div>
				<div>
					<label for="email">Email</label>
					<input name="email" placeholder="john@smith.com" />
				</div>
				<div>
					<label for="password">Password</label>
					<input type="password" name="password" placeholder="" />
				</div>
				<div>
					<button type="submit">Sign Up</button>
				</div>
			</form>
		</div>
	);
}

function Login() {
	const handleLogin = e => {
		e.preventDefault();

		const userData = {
			email: e.target.email.value,
			password: e.target.password.value,
		};

		API.login(userData, data => console.log("Logged in " + JSON.stringify(data)));
	};

	return (
		<div className={styles.homeform}>
			<form onSubmit={handleLogin}>
				<div>
					<input name="email" placeholder="john@smith.com" />
				</div>
				<div>
					<input type="password" name="password" placeholder="" />
				</div>
				<div>
					<button type="submit">Log In</button>
				</div>
			</form>
		</div>
	);
}

export default function Home() {
	return (
		<>
			<Head>
				<title>Easy Trade</title>
				<meta name="description" content="Paper-money Stock Market" />
				<meta name="viewport" content="width=device-width, initial-scale=1" />
				<link rel="icon" href="/favicon.ico" />
			</Head>
			<main className={styles.main}>
				<div className={styles.center}>
					<h1>Easy Trade</h1>
				</div>
				<div className={styles.formswrapper}>
					<Signup />
					<Login />
				</div>
			</main>
		</>
	)
}
