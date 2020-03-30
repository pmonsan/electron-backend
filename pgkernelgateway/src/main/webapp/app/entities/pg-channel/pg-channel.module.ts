import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgChannelComponent,
  PgChannelDetailComponent,
  PgChannelUpdateComponent,
  PgChannelDeletePopupComponent,
  PgChannelDeleteDialogComponent,
  pgChannelRoute,
  pgChannelPopupRoute
} from './';

const ENTITY_STATES = [...pgChannelRoute, ...pgChannelPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgChannelComponent,
    PgChannelDetailComponent,
    PgChannelUpdateComponent,
    PgChannelDeleteDialogComponent,
    PgChannelDeletePopupComponent
  ],
  entryComponents: [PgChannelComponent, PgChannelUpdateComponent, PgChannelDeleteDialogComponent, PgChannelDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgChannelModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
