import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILoanInstalment } from 'app/shared/model/loan-instalment.model';

type EntityResponseType = HttpResponse<ILoanInstalment>;
type EntityArrayResponseType = HttpResponse<ILoanInstalment[]>;

@Injectable({ providedIn: 'root' })
export class LoanInstalmentService {
  public resourceUrl = SERVER_API_URL + 'api/loan-instalments';

  constructor(protected http: HttpClient) {}

  create(loanInstalment: ILoanInstalment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanInstalment);
    return this.http
      .post<ILoanInstalment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(loanInstalment: ILoanInstalment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanInstalment);
    return this.http
      .put<ILoanInstalment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILoanInstalment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILoanInstalment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(loanInstalment: ILoanInstalment): ILoanInstalment {
    const copy: ILoanInstalment = Object.assign({}, loanInstalment, {
      expectedPaymentDate:
        loanInstalment.expectedPaymentDate != null && loanInstalment.expectedPaymentDate.isValid()
          ? loanInstalment.expectedPaymentDate.toJSON()
          : null,
      realPaymentDate:
        loanInstalment.realPaymentDate != null && loanInstalment.realPaymentDate.isValid() ? loanInstalment.realPaymentDate.toJSON() : null,
      statusDate: loanInstalment.statusDate != null && loanInstalment.statusDate.isValid() ? loanInstalment.statusDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.expectedPaymentDate = res.body.expectedPaymentDate != null ? moment(res.body.expectedPaymentDate) : null;
      res.body.realPaymentDate = res.body.realPaymentDate != null ? moment(res.body.realPaymentDate) : null;
      res.body.statusDate = res.body.statusDate != null ? moment(res.body.statusDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((loanInstalment: ILoanInstalment) => {
        loanInstalment.expectedPaymentDate = loanInstalment.expectedPaymentDate != null ? moment(loanInstalment.expectedPaymentDate) : null;
        loanInstalment.realPaymentDate = loanInstalment.realPaymentDate != null ? moment(loanInstalment.realPaymentDate) : null;
        loanInstalment.statusDate = loanInstalment.statusDate != null ? moment(loanInstalment.statusDate) : null;
      });
    }
    return res;
  }
}
