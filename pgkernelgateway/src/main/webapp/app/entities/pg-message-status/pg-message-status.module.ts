import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgMessageStatusComponent,
  PgMessageStatusDetailComponent,
  PgMessageStatusUpdateComponent,
  PgMessageStatusDeletePopupComponent,
  PgMessageStatusDeleteDialogComponent,
  pgMessageStatusRoute,
  pgMessageStatusPopupRoute
} from './';

const ENTITY_STATES = [...pgMessageStatusRoute, ...pgMessageStatusPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgMessageStatusComponent,
    PgMessageStatusDetailComponent,
    PgMessageStatusUpdateComponent,
    PgMessageStatusDeleteDialogComponent,
    PgMessageStatusDeletePopupComponent
  ],
  entryComponents: [
    PgMessageStatusComponent,
    PgMessageStatusUpdateComponent,
    PgMessageStatusDeleteDialogComponent,
    PgMessageStatusDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgMessageStatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
