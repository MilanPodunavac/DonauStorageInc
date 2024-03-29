import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IStorageCard, defaultValue } from 'app/shared/model/storage-card.model';
import { useAppSelector } from 'app/config/store';
import { log } from 'react-jhipster';

const initialState: EntityState<IStorageCard> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/xt/storage-cards';

// Actions

export const getEntities = createAsyncThunk('storageCard/fetch_entity_list', async ({ query, page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?${query ? `businessYearId.equals=${query}&` : ''}${
    sort ? `page=${page}&size=${size}&sort=${sort}&` : ''
  }cacheBuster=${new Date().getTime()}`;
  return axios.get<IStorageCard[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'storageCard/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IStorageCard>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'storageCard/create_entity',
  async (entity: IStorageCard, thunkAPI) => {
    const result = await axios.post<IStorageCard>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'storageCard/update_entity',
  async (entity: IStorageCard, thunkAPI) => {
    const result = await axios.put<IStorageCard>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'storageCard/partial_update_entity',
  async (entity: IStorageCard, thunkAPI) => {
    const result = await axios.patch<IStorageCard>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'storageCard/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IStorageCard>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const generateAnalytics = createAsyncThunk(
  'storageCard/analytics',
  async (entity: IStorageCard, thunkAPI) => {
    console.log(entity);
    const result = await axios.put<string>(`${apiUrl}/${entity.id}/analytics`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));

    console.log(result);
    window.open(result.data, '_blank');
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const StorageCardSlice = createEntitySlice({
  name: 'storageCard',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = StorageCardSlice.actions;

// Reducer
export default StorageCardSlice.reducer;
