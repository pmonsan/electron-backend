import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgBatch } from 'app/shared/model/pg-batch.model';

type EntityResponseType = HttpResponse<IPgBatch>;
type EntityArrayResponseType = HttpResponse<IPgBatch[]>;

@Injectable({ providedIn: 'root' })
export class PgBatchService {
  public resourceUrl = SERVER_API_URL + 'api/pg-batches';

  constructor(protected http: HttpClient) {}

  create(pgBatch: IPgBatch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgBatch);
    return this.http
      .post<IPgBatch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pgBatch: IPgBatch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgBatch);
    return this.http
      .put<IPgBatch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPgBatch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPgBatch[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pgBatch: IPgBatch): IPgBatch {
    const copy: IPgBatch = Object.assign({}, pgBatch, {
      expectedEndDate: pgBatch.expectedEndDate != null && pgBatch.expectedEndDate.isValid() ? pgBatch.expectedEndDate.toJSON() : null,
      batchDate: pgBatch.batchDate != null && pgBatch.batchDate.isValid() ? pgBatch.batchDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.expectedEndDate = res.body.expectedEndDate != null ? moment(res.body.expectedEndDate) : null;
      res.body.batchDate = res.body.batchDate != null ? moment(res.body.batchDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pgBatch: IPgBatch) => {
        pgBatch.expectedEndDate = pgBatch.expectedEndDate != null ? moment(pgBatch.expectedEndDate) : null;
        pgBatch.batchDate = pgBatch.batchDate != null ? moment(pgBatch.batchDate) : null;
      });
    }
    return res;
  }
}
