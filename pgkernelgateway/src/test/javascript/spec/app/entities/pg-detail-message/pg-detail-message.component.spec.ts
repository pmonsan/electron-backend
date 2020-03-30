/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgDetailMessageComponent } from 'app/entities/pg-detail-message/pg-detail-message.component';
import { PgDetailMessageService } from 'app/entities/pg-detail-message/pg-detail-message.service';
import { PgDetailMessage } from 'app/shared/model/pg-detail-message.model';

describe('Component Tests', () => {
  describe('PgDetailMessage Management Component', () => {
    let comp: PgDetailMessageComponent;
    let fixture: ComponentFixture<PgDetailMessageComponent>;
    let service: PgDetailMessageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgDetailMessageComponent],
        providers: []
      })
        .overrideTemplate(PgDetailMessageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgDetailMessageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgDetailMessageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgDetailMessage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgDetailMessages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
