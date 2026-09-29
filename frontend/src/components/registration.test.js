import { fireEvent, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Registration from './registration';
import { postForm } from '../api';

jest.mock('../api', () => ({
  getJson: jest.fn(),
  postForm: jest.fn(),
}));

beforeEach(() => {
  localStorage.clear();
  postForm.mockReset();
});

test('registration logs the new user in', async () => {
  postForm.mockResolvedValue({ id: 7, login: 'ann', firstName: 'Ann', role: null });
  render(<Registration />);

  await userEvent.type(screen.getByPlaceholderText('Login'), 'ann');
  await userEvent.type(screen.getByPlaceholderText('password'), 'secret');
  await userEvent.type(screen.getByPlaceholderText('First Name'), 'Ann');
  await userEvent.type(screen.getByPlaceholderText('Last Name'), 'Lee');
  fireEvent.change(screen.getByPlaceholderText('Date of birth'), { target: { value: '2000-02-02' } });
  await userEvent.click(screen.getByRole('button', { name: 'Sign up' }));

  expect(postForm).toHaveBeenCalledWith('/register', {
    firstname: 'Ann', lastname: 'Lee', birthday: '2000-02-02', login: 'ann', password: 'secret',
  });
  expect(await screen.findByText('You login as Ann')).toBeInTheDocument();
  expect(localStorage.getItem('user_idd')).toBe('7');
  expect(localStorage.getItem('user_rolee')).toBeNull();
});
