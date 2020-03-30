/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountFeatureComponent } from 'app/entities/account-feature/account-feature.component';
import { AccountFeatureService } from 'app/entities/account-feature/account-feature.service';
import { AccountFeature } from 'app/shared/model/account-feature.model';

describe('Component Tests', () => {
  describe('AccountFeature Management Component', () => {
    let comp: AccountFeatureComponent;
    let fixture: ComponentFixture<AccountFeatureComponent>;
    let service: AccountFeatureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountFeatureComponent],
        providers: []
      })
        .overrideTemplate(AccountFeatureComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountFeatureComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountFeatureService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountFeature(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountFeatures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
