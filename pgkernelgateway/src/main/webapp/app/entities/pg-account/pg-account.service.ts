import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgAccount } from 'app/shared/model/pg-account.model';

type EntityResponseType = HttpResponse<IPgAccount>;
type EntityArrayResponseType = HttpResponse<IPgAccount[]>;

@Injectable({ providedIn: 'root' })
export class PgAccountService {
  public resourceUrl = SERVER_API_URL + 'api/pg-accounts';

  constructor(protected http: HttpClient) {}

  create(pgAccount: IPgAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgAccount);
    return this.http
      .post<IPgAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pgAccount: IPgAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgAccount);
    return this.http
      .put<IPgAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPgAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPgAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pgAccount: IPgAccount): IPgAccount {
    const copy: IPgAccount = Object.assign({}, pgAccount, {
      openingDate: pgAccount.openingDate != null && pgAccount.openingDate.isValid() ? pgAccount.openingDate.toJSON() : null,
      closingDate: pgAccount.closingDate != null && pgAccount.closingDate.isValid() ? pgAccount.closingDate.toJSON() : null,
      validationDate: pgAccount.validationDate != null && pgAccount.validationDate.isValid() ? pgAccount.validationDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.openingDate = res.body.openingDate != null ? moment(res.body.openingDate) : null;
      res.body.closingDate = res.body.closingDate != null ? moment(res.body.closingDate) : null;
      res.body.validationDate = res.body.validationDate != null ? moment(res.body.validationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pgAccount: IPgAccount) => {
        pgAccount.openingDate = pgAccount.openingDate != null ? moment(pgAccount.openingDate) : null;
        pgAccount.closingDate = pgAccount.closingDate != null ? moment(pgAccount.closingDate) : null;
        pgAccount.validationDate = pgAccount.validationDate != null ? moment(pgAccount.validationDate) : null;
      });
    }
    return res;
  }
}
