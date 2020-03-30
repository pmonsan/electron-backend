import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersonDocument } from 'app/shared/model/person-document.model';
import { AccountService } from 'app/core';
import { PersonDocumentService } from './person-document.service';

@Component({
  selector: 'jhi-person-document',
  templateUrl: './person-document.component.html'
})
export class PersonDocumentComponent implements OnInit, OnDestroy {
  personDocuments: IPersonDocument[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected personDocumentService: PersonDocumentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personDocumentService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersonDocument[]>) => res.ok),
        map((res: HttpResponse<IPersonDocument[]>) => res.body)
      )
      .subscribe(
        (res: IPersonDocument[]) => {
          this.personDocuments = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPersonDocuments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonDocument) {
    return item.id;
  }

  registerChangeInPersonDocuments() {
    this.eventSubscriber = this.eventManager.subscribe('personDocumentListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
