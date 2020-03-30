/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgChannelAuthorizedComponent } from 'app/entities/pg-channel-authorized/pg-channel-authorized.component';
import { PgChannelAuthorizedService } from 'app/entities/pg-channel-authorized/pg-channel-authorized.service';
import { PgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';

describe('Component Tests', () => {
  describe('PgChannelAuthorized Management Component', () => {
    let comp: PgChannelAuthorizedComponent;
    let fixture: ComponentFixture<PgChannelAuthorizedComponent>;
    let service: PgChannelAuthorizedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgChannelAuthorizedComponent],
        providers: []
      })
        .overrideTemplate(PgChannelAuthorizedComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgChannelAuthorizedComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgChannelAuthorizedService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgChannelAuthorized(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgChannelAuthorizeds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
