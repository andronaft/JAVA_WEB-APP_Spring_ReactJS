import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Authorization from './authorization';
import { postForm } from '../api';

jest.mock('../api', () => ({
  getJson: jest.fn(),
  postForm: jest.fn(),
}));

beforeEach(() => {
  localStorage.clear();
  postForm.mockReset();
});

async function logIn(login, password) {
  render(<Authorization />);
  await userEvent.type(screen.getByPlaceholderText('Login'), login);
  await userEvent.type(screen.getByPlaceholderText('password'), password);
  await userEvent.click(screen.getByRole('button', { name: 'Sign in' }));
}

test('successful login remembers the user', async () => {
  postForm.mockResolvedValue({ id: 1, login: 'admin', role: 'admin' });

  await logIn('admin', 'admin');

  expect(postForm).toHaveBeenCalledWith('/login', { login: 'admin', password: 'admin' });
  expect(await screen.findByText('You login as admin')).toBeInTheDocument();
  expect(localStorage.getItem('user_idd')).toBe('1');
  expect(localStorage.getItem('user_rolee')).toBe('admin');
});

test('failed login shows the server message', async () => {
  postForm.mockResolvedValue(['Incorrect login or password']);

  await logIn('admin', 'nope');

  expect(await screen.findByText('Incorrect login or password')).toBeInTheDocument();
  expect(localStorage.getItem('user_idd')).toBeNull();
});
