import { render, screen, within } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Conferences from './conferences';
import { getJson, postForm } from '../api';

jest.mock('../api', () => ({
  getJson: jest.fn(),
  postForm: jest.fn(),
}));

const conferences = {
  amount: 2,
  arrayList: [
    { id: 1, name: 'literary evening', name_room: 'Blue submarine', capacity_room: 72, amount_participant: 0,
      datee: '2030-01-01', timee: '11:00:00', id_participant: null },
    { id: 4, name: 'Why did I cry', name_room: 'Relation in the distance', capacity_room: 2, amount_participant: 1,
      datee: '2030-02-01', timee: '23:00:00', id_participant: '2,' },
  ],
};

beforeEach(() => {
  localStorage.clear();
  getJson.mockReset();
  postForm.mockReset();
  getJson.mockResolvedValue(conferences);
});

test('guests only see the conferences', async () => {
  render(<Conferences />);

  expect(await screen.findByText(/literary evening/)).toBeInTheDocument();
  expect(screen.getByText('participants: 1/2')).toBeInTheDocument();
  expect(screen.queryByRole('button', { name: 'Join conference' })).not.toBeInTheDocument();
  expect(screen.queryByRole('button', { name: 'CANCEL conf' })).not.toBeInTheDocument();
  expect(getJson).toHaveBeenCalledWith('/getAllConference');
});

test('a logged in user can join and the list is reloaded', async () => {
  localStorage.setItem('user_idd', '2');
  localStorage.setItem('user_rolee', 'null');
  postForm.mockResolvedValue(['You joined the conference "literary evening"']);
  render(<Conferences />);

  const buttons = await screen.findAllByRole('button', { name: 'Join conference' });
  await userEvent.click(buttons[0]);

  expect(postForm).toHaveBeenCalledWith('/joinConference', { conference_id: '1' });
  expect(await screen.findByText('You joined the conference "literary evening"')).toBeInTheDocument();
  expect(getJson).toHaveBeenCalledTimes(2);
});

test('an admin gets the management forms', async () => {
  localStorage.setItem('user_idd', '1');
  localStorage.setItem('user_rolee', 'admin');
  postForm.mockResolvedValue(['Conference was cancelled']);
  render(<Conferences />);

  const card = (await screen.findByText(/Why did I cry/)).closest('.card');
  expect(within(card).getByText(/id participant: 2,/)).toBeInTheDocument();
  expect(screen.getByRole('link', { name: 'new conference' })).toBeInTheDocument();

  const cancelForm = within(card).getByRole('button', { name: 'CANCEL conf' }).closest('form');
  await userEvent.type(within(cancelForm).getByPlaceholderText('password'), 'admin');
  await userEvent.click(within(cancelForm).getByRole('button', { name: 'CANCEL conf' }));

  expect(postForm).toHaveBeenCalledWith('/cancelConf', { conference_id: '4', admin_id: '1', admin_password: 'admin' });
  expect(await screen.findByText('Conference was cancelled')).toBeInTheDocument();
});

test('shows an error when the list can not be loaded', async () => {
  getJson.mockResolvedValue(['Request failed, please try again later']);
  render(<Conferences />);

  expect(await screen.findByText('Request failed, please try again later')).toBeInTheDocument();
});
