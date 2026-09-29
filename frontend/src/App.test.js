import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import App from './App';
import { getJson } from './api';

jest.mock('./api', () => ({
  getJson: jest.fn(),
  postForm: jest.fn(),
}));

beforeEach(() => {
  localStorage.clear();
  window.history.pushState({}, '', '/');
  getJson.mockResolvedValue({ arrayList: [], amount: 0 });
});

test('shows the navigation and asks to log in on the account page', () => {
  render(<App />);

  expect(screen.getByRole('link', { name: 'Conferences' })).toBeInTheDocument();
  expect(screen.getByText('You must log in for view information')).toBeInTheDocument();
});

test('navigates between pages without reloading', async () => {
  render(<App />);

  await userEvent.click(screen.getByRole('link', { name: 'Authorization' }));

  expect(window.location.pathname).toBe('/authorization');
  expect(screen.getByRole('button', { name: 'Sign in' })).toBeInTheDocument();
  expect(screen.queryByText('You must log in for view information')).not.toBeInTheDocument();
});
